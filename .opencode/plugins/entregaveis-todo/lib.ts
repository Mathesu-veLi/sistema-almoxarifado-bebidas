/**
 * Funções puras do gatilho da pasta Entregaveis/.
 *
 * Ficam separadas do index.ts para poderem ser testadas sem carregar o runtime de plugins.
 */

export type Entrega = {
  entrega: number
  titulo: string
  fontes: string[]
  criterios: string[]
  observacao?: string
}

export type Mapa = {
  todo: string
  secao: string
  entregas: Entrega[]
}

const PASTA = "entregaveis/"

/** "C:\\repo\\Entregaveis\\x.md" -> "Entregaveis/x.md" (ou undefined se estiver fora da pasta). */
export function caminhoDaEntrega(caminho: unknown): string | undefined {
  if (typeof caminho !== "string") return undefined
  const limpo = caminho.replace(/^file:\/\//, "").replace(/\\/g, "/")
  const baixo = limpo.toLowerCase()
  const i = baixo.indexOf("/" + PASTA)
  if (i >= 0) return limpo.slice(i + 1)
  if (baixo.startsWith(PASTA)) return limpo
  return undefined
}

/** Converte "Entregaveis/Modelagem/*classes*" em RegExp (sem diferenciar maiúsculas). */
export function comoGlob(padrao: string): RegExp {
  const escapado = padrao
    .replace(/[.+^${}()|[\]\\]/g, "\\$&")
    .replace(/\*\*/g, "\u0000")
    .replace(/\*/g, "[^/]*")
    .replace(/\u0000/g, ".*")
  return new RegExp(`^${escapado}$`, "i")
}

export function acharEntrega(mapa: Mapa | undefined, arquivo: string): Entrega | undefined {
  return mapa?.entregas?.find((entrega) => entrega.fontes?.some((fonte) => comoGlob(fonte).test(arquivo)))
}

export function montarLembrete(arquivos: string[], mapa: Mapa | undefined): string {
  const linhas: string[] = [
    "[entregaveis-todo] Um arquivo da pasta Entregaveis/ foi gravado nesta sessão e merece conferência:",
  ]

  for (const arquivo of arquivos) {
    const entrega = acharEntrega(mapa, arquivo)
    linhas.push("", `Arquivo: ${arquivo}`)
    if (entrega) {
      linhas.push(`Entrega relacionada: ${entrega.entrega} — ${entrega.titulo}`)
      linhas.push("Critérios de conferência (de .opencode/entregaveis.json):")
      entrega.criterios.forEach((criterio, i) => linhas.push(`  ${i + 1}. ${criterio}`))
      if (entrega.observacao) linhas.push(`Observação: ${entrega.observacao}`)
    } else {
      linhas.push("Nenhuma entrega está mapeada para este caminho em .opencode/entregaveis.json.")
    }
  }

  const alvo = mapa?.todo ?? "Diversos/TODO.md"
  const secao = mapa?.secao ?? "1.2 As 10 entregas"
  const numeros = arquivos
    .map((arquivo) => acharEntrega(mapa, arquivo)?.entrega)
    .filter((numero): numero is number => typeof numero === "number")

  linhas.push(
    "",
    "Ação esperada, em uma única etapa:",
    numeros.length > 0
      ? `1. Leia o arquivo e compare o conteúdo com os critérios. Se a Entrega ${numeros.join(" e ")} estiver completa E revisada, troque [ ] por [X] na linha dela em ${alvo}, seção "${secao}".`
      : `1. Leia o arquivo e verifique se ele conclui algum item de ${alvo}, seção "${secao}".`,
    "2. Se o arquivo não permitir a verificação (por exemplo, contém apenas um link para uma ferramenta externa como Lucidchart ou Lucidspark), ou se faltar algum critério, NÃO marque nada: diga em uma frase o que falta e o que a equipe precisa anexar.",
    "3. Não altere outras linhas do TODO.md e não escreva o conteúdo da entrega no lugar da equipe: esta verificação é só de conferência.",
  )

  return linhas.join("\n")
}
