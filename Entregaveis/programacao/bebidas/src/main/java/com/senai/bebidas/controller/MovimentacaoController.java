package com.senai.bebidas.controller;

import com.senai.bebidas.dto.MovimentacaoDTO;
import com.senai.bebidas.dto.MovimentacaoRequest;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.security.JwtUtil;
import com.senai.bebidas.service.MovimentacaoService;
import com.senai.bebidas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller de Movimentacoes.
 * Expõe os 3 endpoints do contrato para movimentacoes.
 * Nao existem endpoints de edicao ou exclusao de movimentacao (historico imutavel).
 */
@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    /**
     * POST /api/movimentacoes - registrar entrada ou saida.
     * O usuarioId vem do JWT (nao e enviado pelo cliente).
     * Responde 201 com os dados da movimentacao (incluindo saldo anterior e atual).
     */
    @PostMapping
    public ResponseEntity<MovimentacaoDTO> registrar(@Valid @RequestBody MovimentacaoRequest request,
                                                     @RequestHeader("Authorization") String authorization) {
        String token = extrairToken(authorization);
        String login = jwtUtil.extractUsername(token);
        Long usuarioId = usuarioService.buscarPorLogin(login).getId();

        MovimentacaoDTO dto = movimentacaoService.registrar(request, usuarioId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * GET /api/movimentacoes - historico com filtros opcionais.
     * Filtros: insumoId, setorId, usuarioId, tipo, dataInicio, dataFim.
     * Ordenacao: dataRegistro decrescente e, no empate, id decrescente.
     */
    @GetMapping
    public ResponseEntity<List<MovimentacaoDTO>> listar(
            @RequestParam(required = false) Long insumoId,
            @RequestParam(required = false) Long setorId,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {
        // dataInicio nao pode ser posterior a dataFim (erro de entrada -> 400).
        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new ValidationException("dataInicio nao pode ser posterior a dataFim");
        }
        List<MovimentacaoDTO> lista = movimentacaoService.listar(
                insumoId, setorId, usuarioId, tipo, dataInicio, dataFim);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/movimentacoes/{id} - consultar movimentacao por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> buscarPorId(@PathVariable Long id) {
        MovimentacaoDTO dto = movimentacaoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
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
