# Requisitos Não Funcionais

- **RNF-01:** “O sistema deve suportar pelo menos 200 Pedidos Simultâneos sem risco de perder performance, com tempo de resposta inferior a 1 segundo por tela especialmente durante nos horários de pico. Sendo eles: (Sexta feira a noite, vésperas de feriado e dias mais quentes)”
- **RNF-02:** ”O sistema deverá ser web e responsivo, deverá adaptando-se corretamente as telas de celular, computadores e tablets”
- **RNF-03:** ”O sistema deverá ser compatível corretamente com navegadores mais utilizados no mercado (Chrome, Firefox e Edge )”
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
- **RNF-15:** "A interface deve exigir a seguinte opção: “aceite dos termos de uso” no cadastro, aplicar máscaras para ocultar dados sensíveis nas telas e oferecer opções visíveis para o usuário exportar seus dados ou inativar definitivamente sua conta."
- **RNF-16:** O fluxo de fechamento de pedido deve ser concluído em no máximo 4 cliques e Seguir diretrizes básicas de contraste e legibilidade (WCAG).
- **RNF-17:** “O sistema deve possuir monitoramento ativo de erros e alertas automáticos em caso de falhas críticas ou lentidão acima do esperado”
