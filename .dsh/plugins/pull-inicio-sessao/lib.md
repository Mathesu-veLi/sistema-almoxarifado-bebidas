# Biblioteca pull-inicio-sessao (DSH)

Regras de verificação do repositório no início da sessão, adaptadas para DSH.

Tipos de resultado:
- atualizado: repositório já está em sincronia
- puxado: pull --ff-only executado com sucesso
- pendente: há alterações não commitadas, pull não executado
- divergente: branch local diverge do remoto, pull não executado
- falhou: falha na comunicação com o remoto
- sem-upstream: repositório sem branch upstream configurado
- fora-do-git/sem-git: não é um repositório Git
