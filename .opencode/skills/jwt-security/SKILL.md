---
name: jwt-security
description: Autenticação JWT no Spring Boot 3 com Spring Security, BCrypt, rotas públicas, filtro de token, expiração de 3600 segundos e respostas 401 padronizadas.
---

# JWT Security Skill (Spring Boot 3 + Spring Security)

## 1. O que o contrato exige

- `POST /api/usuarios` e `POST /api/auth/login` são **públicos**.
- Todos os demais endpoints exigem `Authorization: Bearer <JWT>`.
- O token expira em **3600 segundos**.
- A senha é armazenada com **hash seguro** e nunca é devolvida pela API.
- Credenciais inválidas ou usuário inativo retornam **401**.
- A identidade do usuário vem do **JWT**, nunca de um `id` ou `usuarioId` enviado pelo cliente.
- Todos os usuários autenticados têm as mesmas permissões.
- Não há renovação/troca de token, perfis de autorização ou login social no escopo.

## 2. Dependências

No `pom.xml`, além de Spring Web e JPA:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Para JWT, a equipe pode usar **JJWT** (uma das opções mais comuns):

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
```

> A versão pode mudar. O importante é a equipe entender a biblioteca escolhida e conseguir explicá-la na banca.

## 3. Configuração do Spring Security

Use `SecurityFilterChain` (Spring Security 6). Não use `WebSecurityConfigurerAdapter`, que está depreciado.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/usuarios", "/api/auth/login").permitAll()
                .anyRequest().authenticated())
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, erro) -> {
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write("""
                        {"status":401,"mensagem":"Autenticação necessária","timestamp":"%s"}
                        """.formatted(OffsetDateTime.now()));
                }))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Cuidados:

- `permitAll()` **somente** nas duas rotas públicas. Não libere `/api/**` inteiro.
- Sessão `STATELESS`: o token é a única credencial.
- CSRF pode ser desabilitado em API stateless.
- Se o front-end rodar em outra origem, configure CORS de forma consciente; não libere tudo sem necessidade.

## 4. Hash de senha

No cadastro:

```java
String hash = passwordEncoder.encode(request.senha());
Usuario usuario = new Usuario(request.nome(), request.login(), hash);
```

No login:

```java
if (!passwordEncoder.matches(request.senha(), usuario.getSenhaHash())) {
    throw new CredenciaisInvalidasException();
}
```

Regras:

- Nunca grave `senha` em texto puro.
- Nunca devolva `senha` ou `senhaHash` em DTO de resposta.
- Não use MD5/SHA-1 "caseiro"; use BCrypt (ou Argon2) com fator adequado.
- O campo do banco deve comportar o hash (ex.: `VARCHAR(255)`).

## 5. Geração e validação do token

```java
@Service
public class JwtService {

    private final SecretKey chave;
    private final long expiracaoSegundos = 3600;

    public JwtService(@Value("${jwt.secret}") String segredo) {
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
    }

    public String gerar(Usuario usuario) {
        Instant agora = Instant.now();
        return Jwts.builder()
                .subject(usuario.getLogin())
                .claim("usuarioId", usuario.getId())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plusSeconds(expiracaoSegundos)))
                .signWith(chave)
                .compact();
    }

    public String validarERetornarLogin(String token) {
        return Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
```

Cuidados:

- Guarde o segredo em variável de ambiente (`jwt.secret`), nunca no Git.
- O segredo precisa ter tamanho suficiente para o algoritmo (ex.: HS256 exige pelo menos 256 bits).
- Assine e valide a assinatura; não confie apenas no payload decodificado.
- A expiração deve ser de 3600 segundos, conforme o contrato.
- O token deve identificar o usuário autenticado (subject/login e/ou id).

## 6. Filtro JWT

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    // construtor...

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String login = jwtService.validarERetornarLogin(token);
                Usuario usuario = usuarioRepository.findByLogin(login)
                        .filter(Usuario::isAtivo)
                        .orElseThrow(() -> new CredenciaisInvalidasException());

                var auth = new UsernamePasswordAuthenticationToken(
                        usuario, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
```

Cuidados:

- Token ausente, inválido ou expirado deve resultar em **401** nas rotas protegidas.
- Usuário inativo também não deve autenticar.
- Não coloque regra de negócio no filtro; ele só identifica o usuário.
- Se o token for válido, o controller pode obter o usuário via `Authentication`/`Principal`.
- Evite capturar a exceção e seguir como se estivesse autenticado.

## 7. Login e `/me`

```java
@Service
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = repository.findByLogin(request.login())
                .filter(Usuario::isAtivo)
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!encoder.matches(request.senha(), usuario.getSenhaHash())) {
            throw new CredenciaisInvalidasException();
        }

        String token = jwtService.gerar(usuario);
        return new LoginResponse(token, "Bearer", 3600);
    }
}
```

Resposta do login:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiraEm": 3600
}
```

`GET /api/usuarios/me`:

```java
@GetMapping("/me")
public UsuarioResponse me(Authentication authentication) {
    Usuario usuario = (Usuario) authentication.getPrincipal();
    return UsuarioResponse.from(usuario);
}
```

Nunca aceite um `id` de usuário na URL ou no corpo para decidir "quem sou eu". A identidade vem do token.

## 8. Erros de autenticação

- Credenciais inválidas ou usuário inativo: **401** com `{"status":401,"mensagem":"Credenciais inválidas","timestamp":"..."}`.
- Token ausente/inválido/expirado: **401** com `{"status":401,"mensagem":"Autenticação necessária","timestamp":"..."}`.
- Não use 403 para token ausente; 403 é para falta de permissão, e neste projeto todos os autenticados têm as mesmas permissões.
- Não devolva o motivo técnico detalhado (ex.: "assinatura inválida") ao cliente.

## 9. Testes mínimos

- `POST /api/usuarios` sem token → 201.
- `POST /api/auth/login` com senha correta → 200 e token.
- `POST /api/auth/login` com senha errada → 401.
- `POST /api/auth/login` com usuário inativo → 401.
- `GET /api/usuarios` sem token → 401.
- `GET /api/usuarios` com token inválido → 401.
- `GET /api/usuarios` com token válido → 200.
- `GET /api/usuarios/me` → devolve o usuário do token.
- Token expirado → 401.

## 10. Anti-padrões

- Senha em texto puro ou hash fraco.
- Segredo JWT fixo no código ou commitado.
- `permitAll()` em `/api/**`.
- Devolver `senha`/`senhaHash` no JSON.
- Confiar em `usuarioId` enviado pelo cliente.
- Criar perfis/roles fora do escopo.
- Implementar refresh token (não está no contrato).
- Ignorar expiração ou assinatura do token.
- Usar `WebSecurityConfigurerAdapter`.
- Deixar o Spring Security devolver HTML de login em uma API REST.
