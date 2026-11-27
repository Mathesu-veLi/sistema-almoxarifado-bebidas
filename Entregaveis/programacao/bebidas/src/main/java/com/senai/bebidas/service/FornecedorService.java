package com.senai.bebidas.service;

import com.senai.bebidas.dto.FornecedorDTO;
import com.senai.bebidas.dto.FornecedorRequest;
import com.senai.bebidas.entity.Fornecedor;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.repository.FornecedorRepository;
import com.senai.bebidas.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service de Fornecedor.
 * Aplica as regras de negocio de cadastro, consulta, atualizacao, exclusao e inativacao de fornecedores.
 */
@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final InsumoRepository insumoRepository;

    /**
     * Cadastra um novo fornecedor.
     * Nome e CNPJ sao obrigatorios. CNPJ com 14 digitos e unico.
     */
    @Transactional
    public FornecedorDTO cadastrar(FornecedorRequest request) {
        if (fornecedorRepository.existsByCnpj(request.getCnpj())) {
            throw new BusinessException("CNPJ ja cadastrado");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(request.getNome());
        fornecedor.setCnpj(request.getCnpj());
        fornecedor.setTelefone(request.getTelefone());
        fornecedor.setAtivo(true);

        fornecedor = fornecedorRepository.save(fornecedor);
        return toDTO(fornecedor);
    }

    /**
     * Busca fornecedor por ID. Retorna inclusive inativos (ativo = false).
     */
    @Transactional(readOnly = true)
    public FornecedorDTO buscarPorId(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor nao encontrado"));
        return toDTO(fornecedor);
    }

    /**
     * Lista fornecedores.
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos (todos) ou ?ativo=false (apenas inativos).
     * Resultado ordenado por id crescente.
     */
    @Transactional(readOnly = true)
    public List<FornecedorDTO> listar(String ativo) {
        List<Fornecedor> fornecedores;
        if ("todos".equalsIgnoreCase(ativo)) {
            fornecedores = fornecedorRepository.findAll();
        } else if ("false".equalsIgnoreCase(ativo)) {
            fornecedores = fornecedorRepository.findByAtivoFalse();
        } else {
            fornecedores = fornecedorRepository.findByAtivoTrueOrderByIdAsc();
        }
        return fornecedores.stream().map(this::toDTO).toList();
    }

    /**
     * Atualiza nome, CNPJ, telefone e estado ativo do fornecedor.
     * CNPJ deve continuar unico.
     * ativo = true reativa o fornecedor.
     */
    @Transactional
    public FornecedorDTO atualizar(Long id, FornecedorRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor nao encontrado"));

        if (!fornecedor.getCnpj().equals(request.getCnpj())
                && fornecedorRepository.existsByCnpj(request.getCnpj())) {
            throw new BusinessException("CNPJ ja cadastrado");
        }

        fornecedor.setNome(request.getNome());
        fornecedor.setCnpj(request.getCnpj());
        fornecedor.setTelefone(request.getTelefone());
        fornecedor.setAtivo(request.getAtivo());

        fornecedor = fornecedorRepository.save(fornecedor);
        return toDTO(fornecedor);
    }

    /**
     * Exclui o fornecedor se nao houver insumos vinculados.
     * Caso contrario, inativa logica (ativo = false) preservando a referencia nos insumos.
     * Retorna 204 sem corpo em ambos os casos.
     */
    @Transactional
    public void excluirOuInativar(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor nao encontrado"));

        boolean temVinculos = insumoRepository.findByFornecedorId(id).isEmpty() == false;

        if (temVinculos) {
            // Ha insumos vinculados: so inativa, nunca apaga.
            fornecedor.setAtivo(false);
            fornecedorRepository.save(fornecedor);
        } else {
            // Sem vinculacoes: pode excluir definitivamente.
            fornecedorRepository.delete(fornecedor);
        }
    }

    private FornecedorDTO toDTO(Fornecedor f) {
        return new FornecedorDTO(f.getId(), f.getNome(), f.getCnpj(), f.getTelefone(), f.isAtivo());
    }
}
