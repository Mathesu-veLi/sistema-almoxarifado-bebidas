# Colaboradores do Projeto

Fonte única de verdade para a autoria dos commits do Projeto Integrador (Indústria de Bebidas).

## Lista Oficial

| Handle (GitHub) | Nome de exibição no Git | E-mail |
| --- | --- | --- |
| `Mathesu-veLi` | Matheus Levi | `matheuslevit@gmail.com` |
| `LucasRAJ` | Lucas Rafael | `angelicarosasantos99@gmail.com` |
| `israelsouzadev` | `israelsouzadev` | `israelssilva2007@gmail.com` |
| `matheus391` | `matheus391` | `theusilva205@gmail.com` |
| `JK201-thw` | Breno Santana | `brenosantana20202@gmail.com` |

> Os colaboradores `israelsouzadev` e `matheus391` ainda não informaram um nome de exibição.
> Enquanto isso, o handle é usado como nome no commit. Ao definir o nome, atualize esta tabela.

## Regra de Autoria

O projeto é colaborativo: **o commit pertence a quem fez a alteração**, não a uma identidade fixa.

1. Antes de commitar, o agente **pergunta ao usuário** qual colaborador realizou as alterações, oferecendo a lista acima como opções.
2. A identidade escolhida é aplicada **somente naquele commit**, sem alterar a configuração do repositório:

```bash
git -c user.name="Matheus Levi" -c user.email="matheuslevit@gmail.com" commit -m "feat(insumos): cria controller da tela de insumos"
```

3. Proibido usar `git config user.name` / `git config user.email` para trocar o autor, e proibido `--global`.
4. Trabalho em par: o colaborador principal é o autor e os demais entram no rodapé da mensagem:

```text
Co-authored-by: Lucas Rafael <angelicarosasantos99@gmail.com>
```

5. Se o usuário não confirmar a autoria, **não commite**.

## Adicionando um Novo Colaborador

Acrescente uma linha na tabela acima (handle, nome de exibição e e-mail) e faça o commit dessa alteração com a identidade do colaborador responsável pela inclusão.
