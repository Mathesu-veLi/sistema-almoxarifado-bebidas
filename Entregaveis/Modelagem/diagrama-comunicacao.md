# Diagrama de comunicação — draw.io

## Acessar o diagrama

[Clique aqui para abrir o diagrama no draw.io](https://drive.google.com/file/d/1yWzm9rGzrLx3RttTT_vXSHionrNT4NVT/view?usp=sharing)

## Observações

- Cenário representado: `POST /api/movimentacoes`, com mensagens numeradas de 1 a 10 (token JWT até a resposta `201`).
- Objetos: `:Postman`, `:JwtFilter`, `:MovimentacaoController`, `:MovimentacaoService`, `:MovimentacaoRepository`, `:BancoMySQL`.
- Regras ilustradas: RN-03 (lock antes da conferência de saldo), RN-04 (saldo e histórico na mesma transação) e RN-06 (`SAIDA` exige setor ativo).
- Ferramenta: draw.io, com o arquivo fonte no Google Drive.
