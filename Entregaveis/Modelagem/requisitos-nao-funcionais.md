# Requisitos Não Funcionais

- **RNF-01:** “A Api Rest deve suportar requisições simultâneas nas operações de cadastros, consultas e movimentação de estoque sem degradação de performance, mantendo o tempo de resposta médio inferior a 1 segundo por requisição."
- **RNF-02:** ”A Api deve operar exclusivamente sob o protocolo de HTTP/HTTPS seguindo o padrão do Rest, utilizando formato JSON (application/json) e codificação UTF-8 em todas as requisições e respostas”
- **RNF-03:** ”A Api deve garantir interoperabilidade conforme os padrões do protocolo HTTP/1.1 ou acima, permitindo a integração com qualquer cliente HTTP (como Postman, Insomnia ou aplicações externas) através de requisições e respostas padronizadas em JSON.”
- **RNF-04:** ”O sistema deverá ter um backup automático de segurança de 30 em 30 minutos.”
- **RNF-05:** “Os tokens devem ter tempo de expiração no máximo 15 minutos.”
- **RNF-06:** “Os dados deverão ser criptografados em trânsito e em repouso ao acessar diretamente ao banco de dados para consultas.”
- **RNF-07:** “O sistema será multi-tenant, ou seja um único sistema para vários clientes diferentes”
- **RNF-08:** “As senhas devem ser armazenadas de forma criptografadas”
- **RNF-09:** “O acesso às funcionalidades gerenciais deve ser protegido por autenticação via Token (JWT) e controle de perfil (RBAC), bloqueando acessos sem credenciais válidas.”
- **RNF-10:** “O sistema deve ter rastreabilidade, toda movimentação do usuário devera ser registradas pelos logs (usuário, data e hora)”
- **RNF-11:** “O sistema deverá gerenciar transações de banco de dados“
- **RNF-12:** “O back-end deve oferecer uma rota de monitoramento contínuo (/health) verificando a conexão com o banco de dados e ainda possuir política de reinicialização automática em caso de falha inesperada do processo“
- **RNF-13:** O ambiente da aplicação deve ser containerizado (Docker) ou gerenciado por monitor de processos (PM2), garantindo reinicialização automática em menos de 30 segundos após falhas.”
- **RNF-14** “O sistema deve estar em conformidade com a LGPD, permitindo a anonimização de dados pessoais mediante solicitação”
- **RNF-15:** O fluxo de fechamento de pedido deve ser concluído em no máximo 4 cliques e Seguir diretrizes básicas de contraste e legibilidade (WCAG).
- **RNF-16:** “O sistema deve possuir monitoramento ativo de erros e alertas automáticos em caso de falhas críticas ou lentidão acima do esperado”
