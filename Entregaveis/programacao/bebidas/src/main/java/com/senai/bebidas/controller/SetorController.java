package com.senai.bebidas.controller;

import com.senai.bebidas.dto.SetorDTO;
import com.senai.bebidas.dto.SetorRequest;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.service.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Setores.
 * Expõe os 5 endpoints do contrato para setores.
 */
@RestController
@RequestMapping("/api/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    /**
     * POST /api/setores - criar setor.
     * Responde 201 com os dados do setor.
     */
    @PostMapping
    public ResponseEntity<SetorDTO> cadastrar(@Valid @RequestBody SetorRequest request) {
        SetorDTO dto = setorService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * GET /api/setores - listar setores.
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos ou ?ativo=false.
     */
    @GetMapping
    public ResponseEntity<List<SetorDTO>> listar(@RequestParam(required = false) String ativo) {
        validarParametroAtivo(ativo);
        List<SetorDTO> lista = setorService.listar(ativo);
        return ResponseEntity.ok(lista);
    }

    private void validarParametroAtivo(String ativo) {
        if (ativo != null && !"todos".equalsIgnoreCase(ativo)
                && !"true".equalsIgnoreCase(ativo) && !"false".equalsIgnoreCase(ativo)) {
            throw new ValidationException("O parametro ativo deve ser true, false ou todos");
        }
    }

    /**
     * GET /api/setores/{id} - consultar setor por ID.
     * Retorna inclusive inativos (ativo = false).
     */
    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(@PathVariable Long id) {
        SetorDTO dto = setorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/setores/{id} - atualizar e reativar setor.
     * O campo ativo e obrigatorio e permite reativacao.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(@PathVariable Long id,
                                              @Valid @RequestBody SetorRequest request) {
        SetorDTO dto = setorService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/setores/{id} - excluir se sem vinculacoes, senao inativar.
     * Responde 204 sem corpo em ambos os casos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOuInativar(@PathVariable Long id) {
        setorService.excluirOuInativar(id);
        return ResponseEntity.noContent().build();
    }
}
