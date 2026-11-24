package com.senai.bebidas.controller;

import com.senai.bebidas.dto.UsuarioDTO;
import com.senai.bebidas.dto.UsuarioRequest;
import com.senai.bebidas.dto.UsuarioUpdateRequest;
import com.senai.bebidas.security.JwtUtil;
import com.senai.bebidas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioRequest request) {
        UsuarioDTO dto = usuarioService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> me(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring("Bearer ".length());
        String login = jwtUtil.extractUsername(token);
        UsuarioDTO dto = usuarioService.buscarPorLogin(login);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar(@RequestParam(required = false) String ativo) {
        List<UsuarioDTO> lista = usuarioService.listar(ativo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO dto = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequest request) {
        UsuarioDTO dto = usuarioService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
