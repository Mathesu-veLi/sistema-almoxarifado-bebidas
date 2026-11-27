package com.senai.bebidas.controller;

import com.senai.bebidas.dto.InsumoDTO;
import com.senai.bebidas.dto.InsumoRequest;

import com.senai.bebidas.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @PostMapping
    public ResponseEntity<InsumoDTO> cadastrar(@Valid @RequestBody InsumoRequest request) {
        InsumoDTO dto = insumoService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InsumoDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String ativo) {
        List<InsumoDTO> lista = insumoService.listar(nome, ativo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoDTO> buscarPorId(@PathVariable Long id) {
        InsumoDTO dto = insumoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody InsumoRequest request) {
        InsumoDTO dto = insumoService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOuInativar(@PathVariable Long id) {
        insumoService.excluirOuInativar(id);
        return ResponseEntity.noContent().build();
    }
}
