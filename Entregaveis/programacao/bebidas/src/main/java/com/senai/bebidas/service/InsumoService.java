package com.senai.bebidas.service;

import com.senai.bebidas.dto.InsumoDTO;
import com.senai.bebidas.dto.InsumoRequest;
import com.senai.bebidas.entity.Fornecedor;
import com.senai.bebidas.entity.Insumo;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.repository.FornecedorRepository;
import com.senai.bebidas.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;
    private final FornecedorRepository fornecedorRepository;

    @Transactional
    public InsumoDTO cadastrar(InsumoRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
        if (!fornecedor.isAtivo()) {
            throw new BusinessException("Fornecedor inativo não pode ser atribuído a um insumo");
        }
        Insumo insumo = new Insumo();
        insumo.setNome(request.getNome());
        insumo.setUnidadeMedida(Insumo.UnidadeMedida.valueOf(request.getUnidadeMedida()));
        insumo.setEstoqueMinimo(request.getEstoqueMinimo());
        insumo.setFornecedor(fornecedor);
        insumo.setQuantidadeAtual(BigDecimal.ZERO);
        insumo.setAtivo(true);
        insumo = insumoRepository.save(insumo);
        return toDTO(insumo);
    }

    @Transactional(readOnly = true)
    public InsumoDTO buscarPorId(Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado"));
        return toDTO(insumo);
    }

    @Transactional(readOnly = true)
    public Page<InsumoDTO> listar(String nome, Boolean ativo, Pageable pageable) {
        Page<Insumo> pagina = insumoRepository.findByNomeAndAtivo(nome, ativo, pageable);
        return pagina.map(this::toDTO);
    }

    @Transactional
    public InsumoDTO atualizar(Long id, InsumoRequest request) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado"));
        if (request.getFornecedorId() != null && !request.getFornecedorId().equals(insumo.getFornecedor().getId())) {
            Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
            if (!fornecedor.isAtivo()) {
                throw new BusinessException("Fornecedor inativo não pode ser atribuído a um insumo");
            }
            insumo.setFornecedor(fornecedor);
        }
        insumo.setNome(request.getNome());
        insumo.setUnidadeMedida(Insumo.UnidadeMedida.valueOf(request.getUnidadeMedida()));
        insumo.setEstoqueMinimo(request.getEstoqueMinimo());
        insumo.setAtivo(request.isAtivo());
        insumo = insumoRepository.save(insumo);
        return toDTO(insumo);
    }

    @Transactional
    public void excluirOuInativar(Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado"));
        boolean temVinculos = false;
        if (temVinculos) {
            insumo.setAtivo(false);
            insumoRepository.save(insumo);
        } else {
            insumoRepository.delete(insumo);
        }
    }

    private InsumoDTO toDTO(Insumo i) {
        InsumoDTO dto = new InsumoDTO();
        dto.setId(i.getId());
        dto.setNome(i.getNome());
        dto.setUnidadeMedida(i.getUnidadeMedida().name());
        dto.setQuantidadeAtual(i.getQuantidadeAtual());
        dto.setEstoqueMinimo(i.getEstoqueMinimo());
        dto.setFornecedorId(i.getFornecedor().getId());
        dto.setAtivo(i.isAtivo());
        return dto;
    }
}
