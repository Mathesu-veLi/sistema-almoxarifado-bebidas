package com.senai.bebidas.controller;

import com.senai.bebidas.dto.FornecedorDTO;
import com.senai.bebidas.dto.FornecedorRequest;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.service.FornecedorService;
import com.senai.bebidas.util.FiltroAtivo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<FornecedorDTO> cadastrar(@Valid @RequestBody FornecedorRequest request) {
        FornecedorDTO dto = fornecedorService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> listar(@RequestParam(required = false) String ativo) {
        FiltroAtivo.interpretar(ativo); // valida ou lança ValidationException
        List<FornecedorDTO> lista = fornecedorService.listar(ativo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> buscarPorId(@PathVariable Long id) {
        FornecedorDTO dto = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FornecedorRequest request) {
        FornecedorDTO dto = fornecedorService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOuInativar(@PathVariable Long id) {
        fornecedorService.excluirOuInativar(id);
        return ResponseEntity.noContent().build();
    }
}
