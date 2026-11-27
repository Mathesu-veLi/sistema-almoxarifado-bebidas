package com.senai.bebidas.security;

import com.senai.bebidas.dto.MensagemErroDTO;
import com.senai.bebidas.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT.
 * Intercepta requisicoes com cabecalho Authorization: Bearer <token>,
 * valida o token e define o usuario autenticado no contexto de seguranca.
 * Se o token estiver presente mas for invalido, responde 401 com corpo padronizado.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring("Bearer ".length());

            try {
                String login = jwtUtil.extractUsername(token);

                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var usuario = usuarioRepository.findByLogin(login).orElse(null);

                    if (usuario == null || !usuario.isAtivo()) {
                        // Usuario inativo nao pode acessar rotas protegidas nem com token valido.
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        MensagemErroDTO erro = new MensagemErroDTO(
                                401,
                                "Autenticacao necessaria",
                                java.time.OffsetDateTime.now().toString());
                        response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper()
                                .writeValueAsString(erro));
                        return;
                    }

                    UserDetails userDetails = org.springframework.security.core.userdetails.User
                            .builder()
                            .username(usuario.getLogin())
                            .password("")
                            .roles("USUARIO")
                            .build();

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                // Token invalido ou expirado: resposta 401 com corpo padronizado.
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                MensagemErroDTO erro = new MensagemErroDTO(
                        401,
                        "Autenticacao necessaria",
                        java.time.OffsetDateTime.now().toString());
                response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper()
                        .writeValueAsString(erro));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
