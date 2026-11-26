package com.senai.bebidas.controller;

import com.senai.bebidas.dto.MovimentacaoDTO;
import com.senai.bebidas.dto.MovimentacaoRequest;
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

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<MovimentacaoDTO> registrar(@Valid @RequestBody MovimentacaoRequest request,
                                                     @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring("Bearer ".length());
        String login = jwtUtil.extractUsername(token);
        Long usuarioId = usuarioService.buscarPorLogin(login).getId();
        MovimentacaoDTO dto = movimentacaoService.registrar(request, usuarioId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoDTO>> listar(
            @RequestParam(required = false) Long insumoId,
            @RequestParam(required = false) Long setorId,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {
        List<MovimentacaoDTO> lista = movimentacaoService.listar(insumoId, setorId, usuarioId, tipo, dataInicio, dataFim);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> buscarPorId(@PathVariable Long id) {
        MovimentacaoDTO dto = movimentacaoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }
}
