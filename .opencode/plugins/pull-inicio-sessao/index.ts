import { Plugin } from "@opencode/plugin"

import { montarAviso, verificar, type Resultado } from "./lib.ts"

/**
 * Pull no início da sessão.
 *
 * Na primeira chamada ao modelo de cada sessão nova, este plugin olha o repositório Git:
 *
 * - faz `git fetch` para saber se o remoto avançou;
 * - se a árvore de trabalho estiver limpa e a branch estiver só atrás, roda
 *   `git pull --ff-only` (somente avanço rápido, nunca commit de merge);
 * - se houver alterações não commitadas, NÃO faz o pull: injeta no contexto a ordem
 *   para o agente chamar o subagente `commit-agent` e, depois que ele terminar, rodar
 *   `git pull --ff-only`.
 *
 * O plugin nunca commita, nunca faz push, nunca faz `stash`, `reset`, `checkout --`,
 * `rebase` ou `--force`. Quem explica qualquer falha continua sendo o agente.
 */

const MAX_SESSOES_LEMBRADAS = 500

/** Subagentes que não devem disparar a verificação (evita laço com o agente de commit). */
const AGENTES_IGNORADOS = new Set(["commit-agent"])

export default Plugin.define({
  id: "pull-inicio-sessao",
  async setup(ctx) {
    const raiz = ctx.location.directory
    const vistas = new Set<string>()

    await ctx.session.hook("context", async (event) => {
      const e = event as any
      if (AGENTES_IGNORADOS.has(String(e.agent ?? ""))) return

      const sessao = String(e.sessionID ?? "")
      if (!sessao || vistas.has(sessao)) return
      vistas.add(sessao)
      if (vistas.size > MAX_SESSOES_LEMBRADAS) vistas.clear()

      let resultado: Resultado
      try {
        resultado = await verificar(raiz)
      } catch (erro) {
        console.error("[pull-inicio-sessao] falha inesperada:", erro)
        return
      }

      const aviso = montarAviso(resultado)
      if (!aviso) return

      console.log(`[pull-inicio-sessao] ${resultado.tipo} em ${raiz}`)
      if (Array.isArray(e.system)) e.system.push({ type: "text", text: aviso })
    })

    return () => vistas.clear()
  },
})
