package com.senai.bebidas.controller;

import com.senai.bebidas.dto.InsumoDTO;
import com.senai.bebidas.dto.InsumoRequest;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Insumos.
 * Expõe os 5 endpoints do contrato para insumos.
 */
@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    /**
     * POST /api/insumos - cadastrar insumo.
     * Saldo inicial e sempre zero.
     * Responde 201 com os dados do insumo.
     */
    @PostMapping
    public ResponseEntity<InsumoDTO> cadastrar(@Valid @RequestBody InsumoRequest request) {
        InsumoDTO dto = insumoService.cadastrar(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * GET /api/insumos - listar insumos.
     * aceita ?nome= (busca parcial, ignorando maiusculas/minusculas).
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos ou ?ativo=false.
     * Resultado ordenado por nome crescente.
     */
    @GetMapping
    public ResponseEntity<List<InsumoDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String ativo) {
        validarParametroAtivo(ativo);
        List<InsumoDTO> lista = insumoService.listar(nome, ativo);
        return ResponseEntity.ok(lista);
    }

    private void validarParametroAtivo(String ativo) {
        if (ativo != null && !"todos".equalsIgnoreCase(ativo)
                && !"true".equalsIgnoreCase(ativo) && !"false".equalsIgnoreCase(ativo)) {
            throw new ValidationException("O parametro ativo deve ser true, false ou todos");
        }
    }

    /**
     * GET /api/insumos/{id} - consultar insumo por ID.
     * Retorna inclusive inativos (ativo = false).
     */
    @GetMapping("/{id}")
    public ResponseEntity<InsumoDTO> buscarPorId(@PathVariable Long id) {
        InsumoDTO dto = insumoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/insumos/{id} - atualizar cadastro sem modificar o saldo.
     * O campo ativo e obrigatorio e permite reativacao.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InsumoDTO> atualizar(@PathVariable Long id,
                                               @Valid @RequestBody InsumoRequest request) {
        InsumoDTO dto = insumoService.atualizar(id, request);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/insumos/{id} - excluir se sem movimentacoes, senao inativar.
     * Responde 204 sem corpo em ambos os casos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOuInativar(@PathVariable Long id) {
        insumoService.excluirOuInativar(id);
        return ResponseEntity.noContent().build();
    }
}
