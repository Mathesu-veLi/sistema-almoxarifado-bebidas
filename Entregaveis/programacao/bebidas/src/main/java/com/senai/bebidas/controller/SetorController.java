package com.senai.bebidas.controller;

import com.senai.bebidas.dto.SetorDTO;
import com.senai.bebidas.dto.SetorRequest;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.service.SetorService;
import com.senai.bebidas.util.FiltroAtivo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @PostMapping
    public ResponseEntity<SetorDTO> cadastrar(@Valid @RequestBody SetorRequest request) {
        SetorDTO dto = setorService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SetorDTO>> listar(@RequestParam(required = false) String ativo) {
        FiltroAtivo.interpretar(ativo); // valida ou lança ValidationException
        List<SetorDTO> lista = setorService.listar(ativo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(@PathVariable Long id) {
        SetorDTO dto = setorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody SetorRequest request) {
        SetorDTO dto = setorService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOuInativar(@PathVariable Long id) {
        setorService.excluirOuInativar(id);
        return ResponseEntity.noContent().build();
    }
}
