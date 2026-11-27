package com.senai.bebidas.controller;

import com.senai.bebidas.dto.FornecedorDTO;
import com.senai.bebidas.dto.FornecedorRequest;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Fornecedores.
 * Expõe os 5 endpoints do contrato para fornecedores.
 */
@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    /**
     * POST /api/fornecedores - cadastrar fornecedor.
     * Responde 201 com os dados do fornecedor.
     */
    @PostMapping
    public ResponseEntity<FornecedorDTO> cadastrar(@Valid @RequestBody FornecedorRequest request) {
        FornecedorDTO dto = fornecedorService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * GET /api/fornecedores - listar fornecedores.
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos ou ?ativo=false.
     */
    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> listar(@RequestParam(required = false) String ativo) {
        validarParametroAtivo(ativo);
        List<FornecedorDTO> lista = fornecedorService.listar(ativo);
        return ResponseEntity.ok(lista);
    }

    private void validarParametroAtivo(String ativo) {
        if (ativo != null && !"todos".equalsIgnoreCase(ativo)
                && !"true".equalsIgnoreCase(ativo) && !"false".equalsIgnoreCase(ativo)) {
            throw new ValidationException("O parametro ativo deve ser true, false ou todos");
        }
    }

    /**
     * GET /api/fornecedores/{id} - consultar fornecedor por ID.
     * Retorna inclusive inativos (ativo = false).
     */
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> buscarPorId(@PathVariable Long id) {
        FornecedorDTO dto = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/fornecedores/{id} - atualizar ou reativar fornecedor.
     * O campo ativo e obrigatorio e permite reativacao.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> atualizar(@PathVariable Long id,
                                                   @Valid @RequestBody FornecedorRequest request) {
        FornecedorDTO dto = fornecedorService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/fornecedores/{id} - excluir se sem vinculacoes, senao inativar.
     * Responde 204 sem corpo em ambos os casos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOuInativar(@PathVariable Long id) {
        fornecedorService.excluirOuInativar(id);
        return ResponseEntity.noContent().build();
    }
}
