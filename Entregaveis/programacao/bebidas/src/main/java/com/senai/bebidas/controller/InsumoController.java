package com.senai.bebidas.controller;

import com.senai.bebidas.dto.InsumoDTO;
import com.senai.bebidas.dto.InsumoRequest;
import com.senai.bebidas.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<InsumoDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<InsumoDTO> pagina = insumoService.listar(nome, ativo, pageable);
        return ResponseEntity.ok(pagina);
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
