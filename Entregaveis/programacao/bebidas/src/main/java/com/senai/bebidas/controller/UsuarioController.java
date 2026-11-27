package com.senai.bebidas.controller;

import com.senai.bebidas.dto.UsuarioDTO;
import com.senai.bebidas.dto.UsuarioRequest;
import com.senai.bebidas.dto.UsuarioUpdateRequest;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.security.JwtUtil;
import com.senai.bebidas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Usuarios e Autenticacao.
 * Expõe os 7 endpoints do contrato para usuarios.
 * POST /api/usuarios e POST /api/auth/login sao publicos (nao exigem token).
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    /**
     * POST /api/usuarios - cadastro publico de usuario.
     * Responde 201 com os dados do usuario (sem senha).
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioRequest request) {
        UsuarioDTO dto = usuarioService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * GET /api/usuarios/me - dados do usuario autenticado.
     * A identidade vem do JWT, nunca de um id enviado pelo cliente.
     */
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> me(@RequestHeader("Authorization") String authorization) {
        String token = extrairToken(authorization);
        String login = jwtUtil.extractUsername(token);
        UsuarioDTO dto = usuarioService.buscarPorLogin(login);
        return ResponseEntity.ok(dto);
    }

    /**
     * GET /api/usuarios - listar usuarios.
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos ou ?ativo=false.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar(@RequestParam(required = false) String ativo) {
        validarParametroAtivo(ativo);
        List<UsuarioDTO> lista = usuarioService.listar(ativo);
        return ResponseEntity.ok(lista);
    }

    private void validarParametroAtivo(String ativo) {
        if (ativo != null && !"todos".equalsIgnoreCase(ativo)
                && !"true".equalsIgnoreCase(ativo) && !"false".equalsIgnoreCase(ativo)) {
            throw new ValidationException("O parametro ativo deve ser true, false ou todos");
        }
    }

    /**
     * GET /api/usuarios/{id} - consultar usuario por ID.
     * Retorna inclusive inativos (ativo = false).
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO dto = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/usuarios/{id} - atualizar nome, login e estado ativo.
     * Nao permite alterar senha. O campo ativo e obrigatorio e permite reativacao.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody UsuarioUpdateRequest request) {
        UsuarioDTO dto = usuarioService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/usuarios/{id} - inativacao logica do usuario.
     * Responde 204 sem corpo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Extrai o token JWT do cabeçalho Authorization (formato "Bearer <token>").
     */
    private String extrairToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Token nao fornecido");
        }
        return authorization.substring("Bearer ".length());
    }
}
