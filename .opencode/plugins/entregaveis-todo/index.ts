import { Plugin } from "@opencode/plugin"
import { readFile } from "node:fs/promises"
import { join } from "node:path"

import { caminhoDaEntrega, montarLembrete, type Mapa } from "./lib.ts"

/**
 * Gatilho da pasta Entregaveis/.
 *
 * Quando o agente grava (write/edit) um arquivo dentro de Entregaveis/, este plugin
 * localiza a entrega correspondente em .opencode/entregaveis.json e injeta um lembrete
 * no próximo contexto do modelo. O plugin NÃO marca nada e NÃO chama outro modelo:
 * quem compara o conteúdo com os critérios e decide marcar o [X] na seção 1.2 do
 * Diversos/TODO.md é o próprio agente da sessão.
 */

const VALIDADE_MS = 10 * 60 * 1000
const MAX_ARQUIVOS = 5
const FERRAMENTAS_DE_ESCRITA = /write|edit|patch|create/i
const ARQUIVO_DE_CRITERIOS = [".opencode", "entregaveis.json"]

export default Plugin.define({
  id: "entregaveis-todo",
  async setup(ctx) {
    const raiz = ctx.location.directory
    const pendentes = new Map<string, number>()
    const porChamada = new Map<string, string>()

    async function carregarMapa(): Promise<Mapa | undefined> {
      try {
        const bruto = await readFile(join(raiz, ...ARQUIVO_DE_CRITERIOS), "utf8")
        const mapa = JSON.parse(bruto) as Mapa
        return Array.isArray(mapa?.entregas) ? mapa : undefined
      } catch {
        return undefined
      }
    }

    // 1) Antes da gravação: guarda o caminho de destino, porque só aqui o input está disponível.
    await ctx.tool.hook("execute.before", (event) => {
      const e = event as any
      const chamada = String(e.id ?? e.messageID ?? "")
      if (!FERRAMENTAS_DE_ESCRITA.test(String(e.tool ?? ""))) {
        porChamada.delete(chamada)
        return
      }
      const entrada = e.input ?? {}
      const alvo =
        caminhoDaEntrega(entrada.path) ??
        caminhoDaEntrega(entrada.filePath) ??
        caminhoDaEntrega(entrada.file_path) ??
        caminhoDaEntrega(entrada.file)
      if (alvo) porChamada.set(chamada, alvo)
      else porChamada.delete(chamada)
    })

    // 2) Depois da gravação: só entra na fila se a ferramenta terminou sem erro.
    await ctx.tool.hook("execute.after", (event) => {
      const e = event as any
      const chamada = String(e.id ?? e.messageID ?? "")
      const alvo = porChamada.get(chamada)
      porChamada.delete(chamada)
      if (!alvo || e.status !== "completed") return
      pendentes.set(alvo, Date.now())
    })

    // 3) No próximo contexto do modelo: injeta o lembrete uma única vez e limpa a fila.
    await ctx.session.hook("context", async (event) => {
      if (pendentes.size === 0) return

      const agora = Date.now()
      for (const [arquivo, quando] of pendentes) {
        if (agora - quando > VALIDADE_MS) pendentes.delete(arquivo)
      }
      if (pendentes.size === 0) return

      const arquivos = [...pendentes.keys()].slice(0, MAX_ARQUIVOS)
      pendentes.clear()

      const lembrete = montarLembrete(arquivos, await carregarMapa())
      console.log(`[entregaveis-todo] conferindo ${arquivos.join(", ")}`)

      const alvo = event as any
      if (Array.isArray(alvo.system)) alvo.system.push({ type: "text", text: lembrete })
    })

    return () => {
      pendentes.clear()
      porChamada.clear()
    }
  },
})
