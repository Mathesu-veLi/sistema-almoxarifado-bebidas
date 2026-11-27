package com.senai.bebidas.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.bebidas.dto.MensagemErroDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

/**
 * Substitui a resposta padrao do Spring Security (403 sem corpo) pelo contrato:
 * 401 com { "status", "mensagem", "timestamp" } quando o token falta/expira/e invalido.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        escrever(response, HttpServletResponse.SC_UNAUTHORIZED, "Autenticação necessária");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        escrever(response, HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
    }

    private void escrever(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        MensagemErroDTO erro = new MensagemErroDTO(status, mensagem, OffsetDateTime.now().toString());
        response.getWriter().write(objectMapper.writeValueAsString(erro));
    }
}
