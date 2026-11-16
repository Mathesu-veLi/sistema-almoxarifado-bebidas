/**
 * Regras do pull no início da sessão.
 *
 * Ficam separadas do index.ts para poderem ser testadas sem carregar o runtime de plugins.
 * Nada aqui commita, faz push, `stash`, `reset`, `checkout --`, `rebase` ou `--force`.
 */

import { execFile } from "node:child_process"
import { promisify } from "node:util"

const rodar = promisify(execFile)

const TEMPO_LIMITE_MS = 30_000

export type Resultado =
  | { tipo: "fora-do-git" }
  | { tipo: "sem-git" }
  | { tipo: "sem-upstream" }
  | { tipo: "atualizado" }
  | { tipo: "puxado"; commits: number; upstream: string; de: string; para: string }
  | { tipo: "pendente"; arquivos: string[]; commits: number; upstream: string }
  | { tipo: "divergente"; frente: number; atras: number; upstream: string }
  | { tipo: "falhou"; motivo: string; atras?: number; upstream?: string }

type Saida = { ok: boolean; saida: string }

async function git(raiz: string, args: string[]): Promise<Saida> {
  try {
    const { stdout } = await rodar("git", args, {
      cwd: raiz,
      timeout: TEMPO_LIMITE_MS,
      windowsHide: true,
      env: { ...process.env, GIT_TERMINAL_PROMPT: "0", GCM_INTERACTIVE: "Never" },
    })
    return { ok: true, saida: String(stdout).trim() }
  } catch (erro: any) {
    if (erro?.code === "ENOENT") return { ok: false, saida: "ENOENT" }
    const saida = String(erro?.stderr ?? erro?.message ?? erro).trim()
    return { ok: false, saida }
  }
}

function primeiraLinha(texto: string): string {
  return (
    texto
      .split(/\r?\n/)
      .map((linha) => linha.trim())
      .filter(Boolean)[0] ?? ""
  )
}

function contar(saida: string): number {
  const numero = Number.parseInt(saida.trim(), 10)
  return Number.isFinite(numero) && numero > 0 ? numero : 0
}

/** Lê o repositório, atualiza o remoto e decide se há algo seguro a fazer. */
export async function verificar(raiz: string): Promise<Resultado> {
  const dentro = await git(raiz, ["rev-parse", "--is-inside-work-tree"])
  if (!dentro.ok) return dentro.saida === "ENOENT" ? { tipo: "sem-git" } : { tipo: "fora-do-git" }

  const upstream = await git(raiz, ["rev-parse", "--abbrev-ref", "--symbolic-full-name", "@{u}"])
  if (!upstream.ok || !upstream.saida) return { tipo: "sem-upstream" }
  const referencia = upstream.saida

  const estado = await git(raiz, ["status", "--porcelain", "--untracked-files=no"])
  if (!estado.ok) return { tipo: "falhou", motivo: primeiraLinha(estado.saida), upstream: referencia }
  const arquivos = estado.saida
    .split(/\r?\n/)
    .map((linha) => linha.trim())
    .filter(Boolean)

  const busca = await git(raiz, ["fetch", "--quiet"])
  if (!busca.ok) return { tipo: "falhou", motivo: primeiraLinha(busca.saida), upstream: referencia }

  const atras = contar((await git(raiz, ["rev-list", "--count", `HEAD..${referencia}`])).saida)
  if (atras === 0) return { tipo: "atualizado" }

  const frente = contar((await git(raiz, ["rev-list", "--count", `${referencia}..HEAD`])).saida)
  if (arquivos.length > 0) return { tipo: "pendente", arquivos, commits: atras, upstream: referencia }
  if (frente > 0) return { tipo: "divergente", frente, atras, upstream: referencia }

  const antes = (await git(raiz, ["rev-parse", "--short", "HEAD"])).saida || "?"
  const pull = await git(raiz, ["pull", "--ff-only", "--quiet"])
  if (!pull.ok) return { tipo: "falhou", motivo: primeiraLinha(pull.saida), atras, upstream: referencia }
  const depois = (await git(raiz, ["rev-parse", "--short", "HEAD"])).saida || "?"
  return { tipo: "puxado", commits: atras, upstream: referencia, de: antes, para: depois }
}

/** Texto injetado no contexto do modelo, ou undefined quando não há nada a dizer. */
export function montarAviso(resultado: Resultado): string | undefined {
  const cabecalho =
    "[pull-inicio-sessao] Verificação automática do repositório no início da sessão (AGENTS.md, seção 8.1)."

  switch (resultado.tipo) {
    case "puxado":
      return [
        cabecalho,
        `O pull foi feito: \`git pull --ff-only\` trouxe ${resultado.commits} commit(s) de ${resultado.upstream} (${resultado.de} -> ${resultado.para}).`,
        "Nada a fazer: apenas avise o usuário disso em uma frase curta na primeira resposta.",
      ].join("\n")

    case "pendente":
      return [
        cabecalho,
        `Há ${resultado.arquivos.length} arquivo(s) não commitado(s) e o remoto tem ${resultado.commits} commit(s) novo(s) em ${resultado.upstream}. Por isso o pull NÃO foi executado.`,
        "Ordem a seguir antes de atender ao pedido do usuário:",
        "1. Chame o subagente `commit-agent` para commitar as alterações pendentes, com a autoria definida em docs/colaboradores.md, Conventional Commits e commits atômicos.",
        "2. Quando ele terminar, rode `git pull --ff-only` e conte em uma frase o que veio.",
        "3. Se o commit não puder ser feito (autoria indefinida, alteração que não pertence a um colaborador etc.), pare esse passo, avise o usuário e só então atenda ao pedido.",
        "Não use `git stash`, `git reset`, `git checkout --`, `rebase` nem `--force` para contornar.",
      ].join("\n")

    case "divergente":
      return [
        cabecalho,
        `A branch local está ${resultado.frente} commit(s) à frente e ${resultado.atras} atrás de ${resultado.upstream}: o \`pull --ff-only\` não é seguro e não foi executado.`,
        "Avise o usuário em uma frase e peça como ele quer resolver (rebase, merge ou alinhar com o remoto). Não decida sozinho e não use `--force`.",
      ].join("\n")

    case "falhou":
      const detalhe = resultado.upstream
        ? ` (remoto ${resultado.upstream}${resultado.atras ? `, ${resultado.atras} commit(s) pendentes` : ""})`
        : ""
      return [
        cabecalho,
        `Não foi possível atualizar o repositório: ${resultado.motivo}${detalhe}.`,
        "Avise o usuário em uma frase e siga com o pedido dele. Não tente `stash`, `reset`, `checkout --`, `rebase` nem `--force`.",
      ].join("\n")

    default:
      return undefined
  }
}
