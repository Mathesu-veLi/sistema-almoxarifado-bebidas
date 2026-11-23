**Requisitos Funcionais**  
**\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_**

**Usuários**

	  
**RF 01** \- Cadastrar usuário;

* **Principal Ator:** Visitante (Cliente não autenticado);  
    
* **Dados de Entrada:** Nome,Login e Senha (Que São OBRIGATÓRIOS).

* **Dados de saída:**   
  * **201** {id, nome, login, ativo=true};  
  * **400** se faltar campo obrigatório ou se a senha tiver menos de 8 caracteres;  
  * **409** "Login já cadastrado" se o login já existir;


  

* **Descrição:** A API faz a criação de um novo usuário a partir de nome,login e senha. Além disso,ela armazena a senha com hash e devolve o usuário,mas sem a senha.

* **Resumo da rota:** 

  * ### **POST /api/usuarios:** Nome, login e senha obrigatórios; senha com no mínimo 8 caracteres; login único; resposta nunca traz senha.

* **Critérios de aceitação:**  
    
  * **CA 01:** A senha tem que, por obrigação , conter uma quantidade de caracteres maior ou igual a 8\. Se tiver menos,exibe a mensagem 400\.  
  * **CA 02:** O login deve ser único. Se já existir um usuário com o mesmo login, a API retorna HTTP 409 com “Login já cadastrado”. Se o login não existir, a API cria o usuário com ativo=true e retorna HTTP 201 com id, nome, login e ativo — nunca a senha.  
  * **CA 03:** nome,login e senha são obrigatórios. Se estiverem em branco ou ausente exibe a mensagem 400\.  
  * **CA 04:** A resposta nunca contém senha e nem hash.  
  * **CA 05:** Cadastrar usuário com senha de 7 caracteres exibe o HTTP 400\.  
      
* **Ligações:**  
  * **RN-14** (cadastro de usuários e login são públicos)  
  * **RN-15** (senha nunca devolvida)  
  * **Critérios do Contrato:** nome, login e senha obrigatórios; senha com no  
    mínimo 8 caracteres; login único.  
  


* **Verificação:**  
  * **CT01:** Cadastrar usuário válido exibe 201; senha ausente na resposta   
  * **CT02:** Login duplicado exibe 409\.

**RF 02 \-**   Autenticar usuário (login);

* **Principal ator:** Visitante (cliente não autenticado);

* **Dados de entrada:** login e senha.

* **Dados de saída:**   
  * **200** {token, tipo: "Bearer", expiraEm: 3600} (caso seja sucesso)  
  *  **401** “Credenciais inválidas” para login/senha errados ou usuário inativo (Erro)  
  * **400** se login ou senha estiverem ausentes/em branco (Erro de validação)

* **Descrição:** A API valida as credenciais, gera um JWT que identifica o usuário e expira em 3600 segundos,além de que ela nunca retorna senha.

* **Resumo da rota: POST /api/auth/login —** credenciais válidas exibe o HTTP 200 com JWT; inválidas ou usuário inativo exibe HTTP 401; campos ausentes exibe HTTP 400\.

* **Critérios de aceitação:**  
    
  * **CA 01:** Credenciais válidas e usuário ativo exibe HTTP 200 com token JWT, tipo "Bearer" e expiraEm 3600\.  
  * **CA 02:** Credenciais inválidas (login inexistente ou senha errada) ou usuário inativo exibe HTTP 401 com a mensagem "Credenciais inválidas".  
  * **CA 03:** login ou senha ausentes ou em branco exibe HTTP 400\.  
  * **CA 04:** A resposta nunca contém senha nem hash. O token identifica o usuário autenticado e é aceito nas rotas protegidas.


* **Ligações:**  
  * **RN-14** (login público)  
  * **RN-15** (senha nunca devolvida)  
  * **Critérios do Contrato:** campos obrigatórios e 401 para credenciais inválidas ou usuário inativo.

* **Verificação:**   
  * CT03 (login correto exibe **HTTP** 200 e JWT válido por 3600s)   
  * CT04 (login incorreto ou usuário inativo exibe **HTTP** 401\)

**RF 03 \-**   Consultar dados do usuário autenticado

* **Rota protegida:** sim (exige JWT).

* **Principal ator:** Usuário autenticado

* **Dados de entrada:** sem corpo. O token vai no header: Authorization: Bearer \<token\>.

* **Dados de saída:**   
  * **200** {id,nome,login,ativo}  
  * **401** se o token estiver ausente, inválido ou expirado.

* **Descrição:** A API retorna os dados do usuário autenticado, identificado pelo JWT, por meio de GET /api/usuarios/me. A rota não recebe id de usuário.  
    
* **Critérios de aceitação:**  
  * **CA 01:** A identidade do usuário vem do token JWT, nunca de um id enviado pelo cliente.  
  * **CA 02:** Com token válido, a API retorna HTTP 200 com id, nome, login e ativo do usuário dono do token.  
  * **CA 03:** Com token ausente, inválido ou expirado, a API retorna HTTP 401 com a mensagem "Autenticação necessária".  
  * **CA 04:** A resposta nunca contém senha nem hash.


* **Ligações:**  
  * **RN-14** (rota protegida)  
  * **RN-15** (senha nunca devolvida).

* **Verificações:**   
  * CT05 (sem token exibe HTTP 401\) 

* **Teste complementar:** com token válido, GET /api/usuarios/me exibe HTTP 200 com os dados do usuário do token.

     **RF 04 \-** Listar usuários

* **Rota protegida:** sim.

* **Ator principal:** Usuário autenticado

* **Dados de entrada:** sem corpo. Parâmetro opcional ?ativo=true, ?ativo=false ou ?ativo=todos.  
    
* **Dados de saída:**  
  * **200** com array de usuários {id, nome, login, ativo}. Sem parâmetro, retorna somente ativos.  
  * **400** se ativo tiver valor diferente de true, false ou todos.  
  * **401** "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

* **Descrição:** A API lista os usuários cadastrados conforme o filtro ativo. Por padrão, retorna apenas os ativos, em ordem de id crescente, sem senha  
  nem hash. Lista vazia retorna \[\].  
    
* **Critérios de aceitação:**  
  * **CA 01:** Sem o parâmetro ativo, retorna somente usuários ativos.  
  * **CA 02:** ?ativo=true retorna somente ativos, ?ativo=false retorna somente inativos; ?ativo=todos retorna ativos e inativos.  
  * **CA 03:** A ordem é id crescente.  
  * **CA 04:** A resposta nunca contém senha nem hash; lista vazia retorna \[\].  
  * **CA 05:** Valor inválido de ativo exibe HTTP 400\.  
  * **CA 06:** Sem token válido exibe HTTP 401 "Autenticação necessária".


* **Ligações:** RN-09 (listagem padrão e filtro ativo), RN-14 (rota protegida) e RN-15 (senha nunca devolvida).  
    
* **Verificação:** CT18 (?ativo=todos inclui inativos) e CT05 (sem token exibe HTTP 401).  
    
* **Teste complementar:** sem parâmetro exibe só ativos; ?ativo=false exibe só inativos; ordem id crescente.  
    
    
  **RF 05 \-** Consultar usuário por ID  
    
* **Rota protegida:** Sim  
    
* **Ator principal:** Usuário autenticado  
    
* **Dados de entrada:** ... {id}  na URL (GET /api/usuarios/{id}). Sem corpo.  
    
* **Dados de saída:**

  * **200** com {id, nome, login, ativo}. Inclui usuário inativo, com ativo=false.  
  * **404** "Usuário não encontrado" se o id não existir.  
  * **401** "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

    


* **Descrição:** A API retorna os dados do usuário correspondente ao id informado, incluindo usuários inativos. A resposta nunca contém senha nem hash.


  

* **Critérios de aceitação:**  
  * **CA 01:** Usuário ativo existente exibe HTTP 200 com id, nome, login e ativo=true.  
  * **CA 02:** Usuário inativo exibe HTTP 200 com ativo=false.  
  * **CA 03:** id inexistente exibe HTTP 404 com "Usuário não encontrado".  
  * **CA 04:** A resposta nunca contém senha nem hash.  
  * **CA 05:** Sem token válido exibe HTTP 401 com "Autenticação necessária".


* **Ligações:**   
  * **RN-09** (inativo consultável por ID)  
  * **RN-14** (rota protegida)  
  * **RN-15** (sem senha)


* **Verificação:**    
  * CT05 (sem token,exibe HTTP 401\)


*  **Testes complementares:**  
  * **usuário ativo existente** exibe HTTP 200;  
  * **usuário inativo** exibe HTTP 200 com ativo=false;  
  * **id inexistente** exibe HTTP 404\.

**RF 06 \-** Atualizar usuário

* **Rota protegida:** sim.

* **Ator principal:** Usuário autenticado.

* **Dados de entrada:** id do usuário na URL (PUT /api/usuarios/{id}) e corpo com nome, login e ativo. O campo ativo é obrigatório.

* **Dados de saída:**  
  * **200** com {id, nome, login, ativo} atualizados.  
  * **400** se algum campo obrigatório estiver ausente ou inválido.  
  * **404** "Usuário não encontrado" se o id não existir.  
  * **409** "Login já cadastrado" se o login já pertencer a outro usuário.  
  * **401** "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

* **Descrição:** A API atualiza nome, login e situação ativa do usuário. Não altera a senha. O login continua único. Enviar ativo=true reativa um usuário inativo.

* **Critérios de aceitação:**  
  * **CA 01:** Dados válidos exibe HTTP 200 com os dados atualizados.  
  * **CA 02:** Login já usado por outro usuário exibe HTTP 409 "Login já cadastrado".  
  * **CA 03:** ativo=true em usuário inativo exibe HTTP 200 com ativo=true (reativação).  
  * **CA 04:** A senha não é alterada e a resposta nunca contém senha nem hash.  
  * **CA 05:** id inexistente exibe HTTP 404 "Usuário não encontrado".  
  * **CA 06:** Sem token válido → HTTP 401 "Autenticação necessária".  
  * **CA 07:** Campo obrigatório ausente → HTTP 400\.

* **Ligações:**   
  * **RN-10** (reativação)  
  * **RN-14** (rota protegida)  
  * **RN-15** (senha nunca devolvida)  
  * **Critérios do Contrato:** login único e campos obrigatórios.

* **Verificação:**   
  * **CT17** (reativar por PUT exibe HTTP 200 com ativo=true)   
  * **CT05** (sem token \=  HTTP	401). 


* **Testes complementares:** login duplicado exibe HTTP 409; id inexistente exibe HTTP 404; senha **inalterada.**

  **RF 07 \-** Inativar usuário


* **Rota protegida:** Sim  
    
* **Ator principal:** Usuário autenticado  
    
* **Dados de entrada:** Somente o ID na URL DELETE /api/usuarios/{id}. Sem corpo.  
    
* **Dados de saída:**  
  * **204:** Sem corpo.  
  * **404:** ID inexistente. Exibe a mensagem ‘’usuário não encontrado”.  
  * **401:** Para autenticação necessária se o token estiver ausente,inválido ou expirado.


* **Descrição:** O registro não apaga. Assim como também o histórico não é apagado. O usuário continua consultável por ID, com ativo=false.


* **Critérios de aceitação:**  
  * **CA 01:** DELETE de usuário existente \= HTTP 204 sem corpo. O registro é inativado com ativo=false, não apagado do banco.  
  * **CA 02:** O histórico continua,sem ser apagado.  
  * **CA 03:** O usuário continua consultável por meio do ID, com ativo=false.  
  * **CA 04:** id inexistente exibe HTTP 404 com uma mensagem informando que o usuário não foi encontrado.  
  * **CA 05:** Caso não exista o token,exibirá HTTP 401 para autenticação.


* **Ligações:**  
  * **RN-08** (preservar histórico e inativar)  
  * **RN-09** **(**inativo consultável por ID)  
  * **RN-14** (rota protegida).

    


* **Verificação:**  
  * **CT05** (sem token \= HTTP 401\)

* **Testes complementares:**  
  * **DELETE de usuário existente** \= HTTP 204;  
  * **GET /api/usuarios/{id} depois** exibe HTTP 200 com ativo=false; listagem padrão não mostra o usuário; ?ativo=todos mostra.

**Setores**

**RF 08 \-** Criar setor	

* **Nota:** Todas as rotas, exceto RF-01 e RF-02, são protegidas por JWT e têm como ator principal o Usuário autenticado.

* **Endpoint:** POST /api/setores	  
    
* **Entrada:** {nome, descricao}

* **Sucesso:** 	201 com {id, nome, descricao, ativo}

* **Erros:** 400 para nome obrigatório; 409 para nome já usado entre ativos; 401 “Autenticação necessária” se o token estiver ausente, inválido ou expirado.

* **Ligações:**  
  * **RN-14** (rota protegida)  
  * **Critérios do Contrato:** nome obrigatório e nome único entre ativos.  
  


* **Casos de teste:**   
  * **CT06**   
  * **CT05**

    

* **Testes complementares:** criar setor sem nome \= HTTP 400; criar setor com nome já usado por outro ativo \= HTTP 409; conferir ativo=true na resposta 201\.

**RF 09 \-** Listar setores

* **Endpoint:** GET /api/setores


* **Entrada:** ?ativo=true/false/todos  
    
* **Sucesso:** 200 com array de setores {id, nome, descricao, ativo}. Sem o parâmetro ativo, retorna somente ativos. Ordem por id crescente. Lista vazia retorna \[\].  
    
* **Erros:** 400 se ativo tiver valor diferente de true, false ou todos. 401 para autenticação necessária se o token estiver ausente,inválido ou expirado.

* **Ligações:**   
  * **RN-09** (listagem padrão e filtro) 	  
  * **RN-14** (rota protegida).  
* **Casos de teste:**  
  * **CT18** (?ativo=todos inclui inativos)   
  * **CT05** (sem token \= HTTP 401).

* **Testes complementares:**   
  * **sem parâmetro \=** só ativos;   
  * **?ativo=false \=** só inativos;   
  * **ordem por id.**

**RF 10 \-** Consultar setor por ID

* **Endpoint:** GET /api/setores/{id}

* **Entrada:** ID na URL

* **Sucesso:**200 {id, nome, descricao, ativo}, inclusive inativo

* **Erros:**   
  * 404 “Setor não encontrado” se o id não existir.  
  * 401 “Autenticação necessária” sem token válido.

* **Ligações:**  
  * **RN-09** (inativo consultável por ID)   
  * **RN-14** (rota protegida).

* **Casos de teste:**  
  *  **CT05** (sem token exibe HTTP 401).

* **Testes complementares:**  
  * **setor ativo existente \=** HTTP 200;  
  * **setor inativo \=** HTTP 200 com ativo=false;  
  * **id inexistente \=** HTTP 404\.  
    

**RF 11 \-** Atualizar e reativar setor

* **Endpoint:** PUT /api/setores/{id}

* **Entrada:** \`id\` na URL e corpo com \`{nome, descricao, ativo}\`, sendo o campo \`ativo\` obrigatório.

* **Sucesso:** 200 com \`{id, nome, descricao, ativo}\` atualizados (\`ativo=true\` reativa o setor conforme RN-10; \`ativo=false\` não apaga o histórico conforme RN-08).

* **Erros:**  
  * **400:** Campos obrigatórios ausentes/inválidos.  
  * **404:** ID inexistente ("Setor não encontrado").  
  * **409:** Nome já usado entre os setores ativos (se já pertencer a outro ativo; regra de unicidade específica por nome entre ativos).  
  * **401:** Sem token ("Autenticação necessária").

* **Ligações:**  
  * **RN-08** (preservar histórico)  
  * **RN-10** (reativação)  
  * **RN-14** (rota protegida)  
  * **Critérios do Contrato:** nome único entre ativos.

* **Casos de teste:**   
  * **CT17** (reativar por PUT \= HTTP 200 com \`ativo=true\`)   
  * **CT05** (sem token \= HTTP 401).

* **Testes complementares:**  
  * **Nome já usado por outro setor ativo \=** HTTP 409\.  
  * **ID inexistente \=** HTTP 404\.  
  * **\`ativo=false\`** mantém o histórico intacto.

   
**RF 12 \-** Excluir ou inativar setor

* **Endpoint:** DELETE /api/setores/{id}

* **Entrada:** apenas o ID  na URL, sem corpo na requisição.

* **Sucesso:** 204 sem corpo.  
  * Se o setor não tiver vínculos: pode ser excluído fisicamente.  
  * Se o setor tiver vínculos (por exemplo, movimentações): é inativado com ativo=false e o histórico é preservado (exclusão lógica).

* **Erros:**  
  * 404: ID inexistente ("Setor não encontrado").  
  * 401: Sem token ("Autenticação necessária").  
      
* **Ligações:** RN-08 (preservar histórico e inativar) e RN-14 (rota protegida).

* **Casos de teste:** CT05 (sem token \= HTTP 401).  
    
* **Testes complementares:**  
  * **Setor sem vínculos \=** DELETE 204; GET por ID depois → 404 (excluído fisicamente).  
  * **Setor com movimentação \=** DELETE 204; GET por ID depois → 200 com ativo=false e histórico intacto.


  


  **Fornecedores**


  

**RF 13 \-** Criar fornecedor

* **Endpoint:** POST /api/fornecedores  
    
* **Entrada:** {nome, cnpj, telefone}  
    
* **Sucesso:** 201 {id, nome, cnpj, telefone, ativo}  
    
* **Erros:** 400 nome/CNPJ obrigatórios ou CNPJ sem 14 dígitos; 409 CNPJ já cadastrado; 401 (autenticação necessária)  
* **Ligações:**  
  * **RN-14** (rota protegida)  
  * **Critérios do Contrato:** nome e CNPJ obrigatórios; CNPJ com 14 dígitos e único.  
  * **CT:** CT06, CT05

**RF 14 \-** Listar fornecedores

* **Endpoint:** GET /api/fornecedores  
    
* **Entrada:** ?ativo=true/false/todos  
    
* **Sucesso:** 200, array, ativos por padrão, id crescente  
    
* **Erros:** 400 ativo inválido; 401 (autenticação necessária)  
    
* **Ligações:** RN-09, RN-14  
    
* **CT:** CT18, CT05

* **Informações importantes:** lista vazia retorna \[\]

**RF 15 \-** Consultar fornecedor por ID

* **Endpoint:** GET /api/fornecedores/{id}  
    
* **Entrada:** id na URL  
    
* **Sucesso:** 200 {id, nome, cnpj, telefone, ativo}, inclusive inativo  
    
* **Erros:** 404; 401 (autenticação necessária)  
    
* **Ligações:** RN-09, RN-14  
    
* **CT:** CT05 \+ teste 404/inativo

* **Informações importantes:** se o fornecedor estiver inativo, ativo=false;

**RF 16 \-** Atualizar e reativar fornecedor

* **Endpoint:** PUT /api/fornecedores/{id}  
    
* **Entrada:** id \+ {nome, cnpj, telefone, ativo}  
    
* **Sucesso:** 200 com dados atualizados  
    
* **Erros:** 400; 404; 409 CNPJ já cadastrado; 401 (autenticação necessária)  
    
* **Ligações:**  
  * **RN-10** (reativação)  
  * **RN-11** (fornecedor inativo não entra em novo vínculo)  
  * **RN-14** (rota protegida)  
  * **Critérios do Contrato:** CNPJ único e campos obrigatórios.  
  * **CT:** CT17, CT05 \+ teste 409

* **Informações importantes:**   
  * O campo ativo é obrigatório no PUT.  
  * O 409 é por CNPJ já cadastrado.

**RF 17-** Excluir ou inativar fornecedor

* **Endpoint:** DELETE /api/fornecedores/{id}  
    
* **Entrada:** id na URL  
    
* **Sucesso: 204** sem corpo; físico se sem vínculos, lógico se associado a insumos  
    
* **Erros: 404** (fornecedor não encontrado)**; 401** (autenticação necessária)  
    
* **Ligações: RN-08, RN-11, RN-14**  
    
* **CT: CT05 \+ testes físico/lógico**

**Insumos**

**RF 18 \-** Criar insumo

* **Endpoint:** POST /api/insumos

* **Entrada:** {nome, unidadeMedida, estoqueMinimo, fornecedorId}

* **Sucesso:** 201 {id, nome, unidadeMedida, quantidadeAtual: 0, estoqueMinimo, fornecedorId, ativo}

* **Erros:** 400 para campos obrigatórios, unidade inválida (somente UN, KG ou L) ou estoque mínimo negativo; 404 "Fornecedor não encontrado" se o fornecedorId não existir; 409 fornecedor inativo; 401 "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

* **Ligações:**  
  * **RN-01** (saldo nasce em zero)  
  * **RN-11** (fornecedor ativo no vínculo)  
  * **RN-14** (rota protegida)  
  * **Critérios do Contrato:** campos obrigatórios; unidade de medida válida; estoque mínimo não negativo.  
  * **CT:** CT07, CT05

* **Informações importantes:** quantidadeAtual não é aceita no corpo; o saldo nasce em 0\.

**RF 19 \-** Listar e pesquisar insumos

* **Endpoint:** GET /api/insumos

* **Entrada:** ?nome=... e ?ativo=true/false/todos

* **Sucesso:** 200, array, busca parcial sem caixa, ordem alfabética

* **Erros:** 400 se o parâmetro ativo tiver valor diferente de true, false ou todos; 401 "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

* **Ligações:** RN-09, RN-16, RN-14

* **CT:** CT08, CT18, CT05

* **Informações importantes:** sem o parâmetro ativo, retorna somente ativos; lista vazia retorna \[\]

**RF 20 \-** Consultar insumo por ID

* **Endpoint:** GET /api/insumos/{id}

* **Entrada:** id na URL

* **Sucesso:** 200 {id, nome, unidadeMedida, quantidadeAtual, estoqueMinimo, fornecedorId, ativo}, inclusive inativo

* **Erros:** 404 "Insumo não encontrado"; 401 "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

* **Ligações:** RN-09, RN-14

* **CT:** CT05 \+ teste 404/inativo

**RF 21 \-** Atualizar insumo sem alterar saldo

* **Endpoint:** PUT /api/insumos/{id}

* **Entrada:** id \+ {nome, unidadeMedida, estoqueMinimo, fornecedorId, ativo}

* **Sucesso:** 200 com dados atualizados; quantidadeAtual inalterada

* **Erros:*** 400 para campo obrigatório ausente, estoqueMinimo negativo ou
  unidade inválida; 404 "Insumo não encontrado" somente se o id não existir;
  409 unidade imutável após movimentação ou fornecedor inativo;
  401 "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

* **Ligações:**  
  * **RN-01** (saldo só muda por movimentação)  
  * **RN-10** (reativação)  
  * **RN-11** (fornecedor ativo no vínculo)  
  * **RN-14** (rota protegida)  
  * **Critérios do Contrato:** unidade imutável após movimentação; campos obrigatórios.  
  * **CT:** CT17, CT05 \+ testes unidade/fornecedor

* **Informações importantes:** o PUT pode reativar o insumo com ativo=true (RN-10);
  quantidadeAtual permanece inalterada; 400 para estoqueMinimo negativo ou
  campo obrigatório ausente.

**RF 22 \-** Excluir ou inativar insumo

* **Endpoint:** DELETE /api/insumos/{id}

* **Entrada:** id na URL

* **Sucesso:** 204 sem corpo; físico se sem movimentações, lógico se com histórico; se o id existir, responde 204 mesmo que o insumo já esteja inativo.

* **Erros:** 404 "Insumo não encontrado" somente se o id não existir;
  401 "Autenticação necessária" se o token estiver ausente, inválido ou expirado.

* **Ligações:** RN-08, RN-14

* **CT:** CT16, CT05 \+ testes físico/lógico

**Movimentações e alertas**

**RF 23 \-** Registrar entrada ou saída

* **Rota protegida:** sim.

* **Ator principal:** Usuário autenticado.  
    
* **Dados de entrada:**  
    
  * **ENTRADA:** {insumoId, tipo: "ENTRADA", quantidade, dataMovimentacao,lote?, validade?}  
      
  * **SAIDA:** {insumoId, tipo: "SAIDA", quantidade, setorId, dataMovimentacao}  
       
  * usuarioId NÃO vai no corpo: vem do JWT.

* **Dados de saída:**  
  * 201 com {id, insumoId, tipo, quantidade, setorId, usuarioId, dataMovimentacao, dataRegistro, lote, validade, saldoAnterior, saldoAtual}.
    
  * 400: 400: campo obrigatório ausente; tipo diferente de ENTRADA ou SAIDA; quantidade menor ou igual a zero; quantidade não inteira quando a unidade for UN ou com mais de 3 casas decimais quando for KG ou L; dataMovimentacao fora do formato YYYY-MM-DD; validade fora de YYYY-MM-DD quando enviada; data futura;
validade anterior à dataMovimentacao; SAIDA com lote/validade.
      
  * 404: insumoId ou setorId inexistente.  
      
  * 409: insumo inativo; setor inativo em SAIDA; estoque insuficiente.  
      
  * 401: “Autenticação necessária” se o token estiver ausente, inválido ou expirado.

* **Descrição:** A API registra ENTRADA ou SAIDA em um único endpoint. ENTRADA soma ao saldo; SAIDA subtrai. O saldo nunca fica negativo. A movimentação e a atualização do saldo são gravadas na mesma transação. O sistema grava o usuário do JWT, a data operacional informada, a data/hora real do registro e os saldos anterior e posterior.

* **Critérios de aceitação:**  
  * **CA 01:** ENTRADA válida exibe HTTP 201; saldoAtual \= saldoAnterior \+ quantidade; histórico criado.  
  * **CA 02:** SAIDA válida com saldo suficiente \=  201; saldoAtual \= saldoAnterior \- quantidade; histórico criado.  
      
  * **CA 03:** SAIDA maior que o saldo exibe HTTP 409 "Quantidade solicitada superior ao estoque disponível"; nada é gravado e o saldo não muda.  
      
  * **CA 04:** quantidade zero ou negativa → 400. Com UN, quantidade não inteira → 400.
Com KG ou L, mais de 3 casas decimais → 400. Nada é gravado.
      
  * **CA 05:** Data futura exibe HTTP 400 "A data da movimentação não pode ser futura".  
      
  * **CA 06:** Insumo inativo exibe HTTP 409 "Insumo inativo". SAIDA com setor inativo exibe HTTP 409\. SAIDA com setor inexistente exibe HTTP 404 "Setor não encontrado".  
      
  * **CA 07:** usuarioId vem do JWT, nunca do corpo; dataRegistro é gerada pelo servidor.  
      
  * **CA 08:** Falha no meio da operação \= rollback integral (RN-04).  
      
  * **CA 09:** Duas saídas simultâneas com saldo insuficiente \= uma é aceita e a outra recebe 409; o saldo não fica negativo.  
      
  * **CA 10:** ENTRADA com lote/validade opcionais \= HTTP 201; valores preservados; validade não anterior à data.  
      
  * **CA 11:** SAIDA não aceita lote/validade.  
      
  * **CA 12:** SAIDA sem setorId exibe HTTP 400\. ENTRADA retorna setorId: null.  
      
  * **CA 13:** ENTRADA com dataMovimentacao retroativa exibe HTTP 201; saldoAnterior é o saldo no instante do registro e saldoAtual \= saldoAnterior \+ quantidade. Nenhuma movimentação anterior é recalculada e a ordem do histórico não muda (RN-13).
    
  * **CA 14:** tipo diferente de ENTRADA/SAIDA = HTTP 400. dataMovimentacao ou validade
fora do formato YYYY-MM-DD = HTTP 400.
      
      
* **Ligações:**  
  * **RN-01 a RN-07, RN-13, RN-14.**  
  * **Critérios do Contrato:** campos obrigatórios da movimentação (insumoId, tipo, quantidade, dataMovimentacao e setorId em SAIDA).

* **Verificação:** CT09, CT10, CT11, CT12, CT13, CT19, CT20, CT21, CT22 e CT05.

**RF 24 \-** Consultar histórico com filtros

* **Endpoint:** GET /api/movimentacoes

* **Entrada:** filtros opcionais insumoId, setorId, usuarioId, tipo, dataInicio, dataFim

* **Sucesso:** 200, array; filtra por dataMovimentacao; ordem dataRegistro desc e id desc. Lista vazia retorna \[\]

* **Erros:** 400 parâmetro inválido (tipo diferente de ENTRADA/SAIDA, id não inteiro, data inválida) ou dataInicio posterior a dataFim.; 401 (“ausente, inválido ou expirado’’)

* **Ligações:** RN-07, RN-13, RN-14.  
  * Critérios do Contrato: filtros válidos e ordenação por dataRegistro desc e id desc.  
  * **CT:** CT14, CT05

**RF 25 \-** Consultar movimentação por ID

* **Endpoint:** GET /api/movimentacoes/{id}

* **Entrada:** id na URL

* **Sucesso:** 200 com a movimentação completa

* **Erros:** 404 “Movimentação não encontrada”; 401 (ausente,inválido ou expirado)

* **Ligações:** RN-07, RN-14

* **CT:** CT05 \+ teste 404

* **Observação:** não existem PUT nem DELETE de movimentação. O histórico é imutável.

**RF 26 \-** Consultar alertas de estoque

* **Endpoint:** GET /api/alertas/estoque

* **Entrada:** sem entrada

* **Sucesso:** 200, array {insumoId, nome, quantidadeAtual, estoqueMinimo, situacao: "CRITICO"}; só ativos com quantidadeAtual \<= estoqueMinimo; ordem por nome; \[\] se vazio

* Inclui o insumo com quantidadeAtual igual ao estoqueMinimo (regra \<=, não \<).

* **Erros:** 401 (ausente,inválido ou expirado)

* **Ligações:** RN-12, RN-14

* **CT:** CT15, CT05

