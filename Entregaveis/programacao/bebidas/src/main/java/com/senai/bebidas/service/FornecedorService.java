package com.senai.bebidas.service;

import com.senai.bebidas.dto.FornecedorDTO;
import com.senai.bebidas.dto.FornecedorRequest;
import com.senai.bebidas.entity.Fornecedor;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.repository.FornecedorRepository;
import com.senai.bebidas.repository.InsumoRepository;
import com.senai.bebidas.util.FiltroAtivo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final InsumoRepository insumoRepository;

    @Transactional
    public FornecedorDTO cadastrar(FornecedorRequest request) {
        if (fornecedorRepository.existsByCnpj(request.getCnpj())) {
            throw new BusinessException("CNPJ já cadastrado");
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(request.getNome());
        fornecedor.setCnpj(request.getCnpj());
        fornecedor.setTelefone(request.getTelefone());
        fornecedor.setAtivo(true);
        fornecedor = fornecedorRepository.save(fornecedor);
        return toDTO(fornecedor);
    }

    @Transactional(readOnly = true)
    public FornecedorDTO buscarPorId(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
        return toDTO(fornecedor);
    }

    @Transactional(readOnly = true)
    public List<FornecedorDTO> listar(String ativo) {
        Boolean filtro = FiltroAtivo.interpretar(ativo);
        List<Fornecedor> fornecedores;
        if (filtro == null) {
            fornecedores = fornecedorRepository.findAll();
        } else if (filtro) {
            fornecedores = fornecedorRepository.findByAtivoTrue();
        } else {
            fornecedores = fornecedorRepository.findByAtivoFalse();
        }
        fornecedores.sort(Comparator.comparing(Fornecedor::getId));
        return fornecedores.stream().map(this::toDTO).toList();
    }

    @Transactional
    public FornecedorDTO atualizar(Long id, FornecedorRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
        if (!fornecedor.getCnpj().equals(request.getCnpj()) && fornecedorRepository.existsByCnpj(request.getCnpj())) {
            throw new BusinessException("CNPJ já cadastrado");
        }
        if (request.getAtivo() == null) {
            throw new ValidationException("O campo ativo é obrigatório");
        }
        fornecedor.setNome(request.getNome());
        fornecedor.setCnpj(request.getCnpj());
        fornecedor.setTelefone(request.getTelefone());
        fornecedor.setAtivo(request.getAtivo());
        fornecedor = fornecedorRepository.save(fornecedor);
        return toDTO(fornecedor);
    }

    @Transactional
    public void excluirOuInativar(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
        
        boolean temVinculos = insumoRepository.existsByFornecedorId(id);
        
        if (temVinculos) {
            fornecedor.setAtivo(false);
            fornecedorRepository.save(fornecedor);
        } else {
            fornecedorRepository.delete(fornecedor);
        }
    }

    private FornecedorDTO toDTO(Fornecedor f) {
        return new FornecedorDTO(f.getId(), f.getNome(), f.getCnpj(), f.getTelefone(), f.isAtivo());
    }
}
