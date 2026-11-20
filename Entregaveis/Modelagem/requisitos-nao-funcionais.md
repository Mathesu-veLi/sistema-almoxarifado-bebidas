# Requisitos Não Funcionais

- **RNF-01:** “O Sistema deve suportar pelo menos 200 Pedidos Simultâneos sem risco de perder performance, com tempo de resposta inferior a 1 segundo por tela especialmente durante nos horários de pico. Sendo eles: (Sexta feira a noite, vésperas de feriado e dias mais quentes)”
- **RNF-02:** ”O Sistema deverá ser web e responsivo, deverá adaptando-se corretamente as telas de celular, computadores e tablets”
- **RNF-03:** ”O Sistema deverá ser compatível corretamente com navegadores mais utilizados no mercado (Chrome, Firefox e Edge )”
- **RNF-04:** ”O Sistema deverá ter um backup automático de segurança de 30 em 30 minutos.”
- **RNF-05:** “O sistema deverá funcionar somente em território Brasileiro.”
- **RNF-06:** “Os dados deverão ser criptografados em trânsito e em repouso ao acessar diretamente ao banco de dados para consultas.”
- **RNF-07:** “O sistema será multi-tenant, ou seja um único sistema para vários clientes diferentes”
- **RNF-08:** “O Sistema poderá ser acessados apenas Localmente ou VPN”
- **RNF-09:** “O Site do sistema poderá ser acessado apenas com dispositivos autorizados/cadastrados registrados na rede através das tecnologias via controle de IP ou certificado.”
- **RNF-10:** “O Sistema deve ter rastreabilidade, toda movimentação do usuário devera ser registradas pelos logs (usuário, data e hora)”
- **RNF-11:** “O sistema deverá gerenciar transações de banco de dados“
- **RNF-12:** “O sistema deve garantir disponibilidade mínima de 99,5% ao mês, especialmente nos horários de pico“
- **RNF-13:** “Em caso de falha crítica, o sistema deve ser restabelecido em no máximo 15 minutos.”
- **RNF-14** “O sistema deve estar em conformidade com a LGPD, permitindo a anonimização de dados pessoais mediante solicitação”
- **RNF-15:** "A interface deve exigir a seguinte opção: “aceite dos termos de uso” no cadastro, aplicar máscaras para ocultar dados sensíveis nas telas e oferecer opções visíveis para o usuário exportar seus dados ou excluir definitivamente sua conta."
- **RNF-16:** O fluxo de fechamento de pedido deve ser concluído em no máximo 4 cliques e Seguir diretrizes básicas de contraste e legibilidade (WCAG).
- **RNF-17:** “O sistema deve possuir monitoramento ativo de erros e alertas automáticos em caso de falhas críticas ou lentidão acima do esperado”
