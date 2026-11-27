package com.senai.bebidas.service;

import com.senai.bebidas.dto.InsumoDTO;
import com.senai.bebidas.dto.InsumoRequest;
import com.senai.bebidas.entity.Fornecedor;
import com.senai.bebidas.entity.Insumo;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.repository.FornecedorRepository;
import com.senai.bebidas.repository.InsumoRepository;
import com.senai.bebidas.repository.MovimentacaoRepository;
import com.senai.bebidas.util.FiltroAtivo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public InsumoDTO cadastrar(InsumoRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
        if (!fornecedor.isAtivo()) {
            throw new BusinessException("Fornecedor inativo não pode ser atribuído a um insumo");
        }
        validarEscala(request.getUnidadeMedida(), request.getEstoqueMinimo());
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
    public List<InsumoDTO> listar(String nome, String ativo) {
        Boolean filtro = FiltroAtivo.interpretar(ativo);
        List<Insumo> lista;
        if (filtro == null) {
            lista = insumoRepository.findByNomeAndAtivo(nome, null);
        } else if (filtro) {
            lista = insumoRepository.findByNomeAndAtivo(nome, true);
        } else {
            lista = insumoRepository.findByNomeAndAtivo(nome, false);
        }
        return lista.stream().map(this::toDTO).toList();
    }

    @Transactional
    public InsumoDTO atualizar(Long id, InsumoRequest request) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado"));
        
        // unidadeMedida é imutável - não pode ser alterada após criação
        if (!insumo.getUnidadeMedida().name().equals(request.getUnidadeMedida())) {
            throw new BusinessException("A unidadeMedida é imutável e não pode ser alterada");
        }
        
        if (request.getFornecedorId() != null && !request.getFornecedorId().equals(insumo.getFornecedor().getId())) {
            Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
            if (!fornecedor.isAtivo()) {
                throw new BusinessException("Fornecedor inativo não pode ser atribuído a um insumo");
            }
            insumo.setFornecedor(fornecedor);
        }
        insumo.setNome(request.getNome());
        // unidadeMedida não alterada
        validarEscala(request.getUnidadeMedida(), request.getEstoqueMinimo());
        insumo.setEstoqueMinimo(request.getEstoqueMinimo());
        if (request.getAtivo() == null) {
            throw new ValidationException("O campo ativo é obrigatório");
        }
        insumo.setAtivo(request.getAtivo());
        insumo = insumoRepository.save(insumo);
        return toDTO(insumo);
    }

    @Transactional
    public void excluirOuInativar(Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado"));
        
        boolean temVinculos = movimentacaoRepository.existsByInsumoId(id);
        
        if (temVinculos) {
            insumo.setAtivo(false);
            insumoRepository.save(insumo);
        } else {
            insumoRepository.delete(insumo);
        }
    }

    private void validarEscala(String unidadeMedida, BigDecimal estoqueMinimo) {
        if (estoqueMinimo != null) {
            int scale = estoqueMinimo.stripTrailingZeros().scale();
            if (scale > 3) {
                throw new BusinessException("O estoqueMinimo não pode ter mais de 3 casas decimais");
            }
            if ("UN".equals(unidadeMedida) && scale > 0) {
                throw new BusinessException("Insumo UN não aceita valores fracionários no estoqueMinimo");
            }
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
