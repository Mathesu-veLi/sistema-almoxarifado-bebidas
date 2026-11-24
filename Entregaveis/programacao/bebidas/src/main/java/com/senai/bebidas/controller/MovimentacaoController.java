package com.senai.bebidas.controller;

import com.senai.bebidas.dto.MovimentacaoDTO;
import com.senai.bebidas.dto.MovimentacaoRequest;
import com.senai.bebidas.security.JwtUtil;
import com.senai.bebidas.service.MovimentacaoService;
import com.senai.bebidas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

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
    public ResponseEntity<Page<MovimentacaoDTO>> listar(
            @RequestParam(required = false) Long insumoId,
            @RequestParam(required = false) Long setorId,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataRegistro").descending().and(Sort.by("id").descending()));
        Page<MovimentacaoDTO> pagina = movimentacaoService.listar(insumoId, setorId, usuarioId, tipo, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> buscarPorId(@PathVariable Long id) {
        MovimentacaoDTO dto = movimentacaoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }
}
