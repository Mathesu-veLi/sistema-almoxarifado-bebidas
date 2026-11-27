package com.senai.bebidas.service;

import com.senai.bebidas.dto.InsumoDTO;
import com.senai.bebidas.dto.InsumoRequest;
import com.senai.bebidas.entity.Fornecedor;
import com.senai.bebidas.entity.Insumo;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.repository.FornecedorRepository;
import com.senai.bebidas.repository.InsumoRepository;
import com.senai.bebidas.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service de Insumo.
 * Aplica as regras de negocio de cadastro, consulta, atualizacao, exclusao e inativacao de insumos.
 */
@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    /**
     * Cadastra um novo insumo.
     * Saldo inicial e sempre zero (quantidadeAtual nao e recebida do cliente).
     * Fornecedor deve estar ativo.
     */
    @Transactional
    public InsumoDTO cadastrar(InsumoRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor nao encontrado"));

        if (!fornecedor.isAtivo()) {
            throw new BusinessException("Fornecedor inativo nao pode ser atribuido a um insumo");
        }

        Insumo insumo = new Insumo();
        insumo.setNome(request.getNome());
        insumo.setUnidadeMedida(Insumo.UnidadeMedida.valueOf(request.getUnidadeMedida()));
        insumo.setEstoqueMinimo(request.getEstoqueMinimo());
        insumo.setFornecedor(fornecedor);
        insumo.setQuantidadeAtual(BigDecimal.ZERO); // saldo comeca em zero (RN-01)
        insumo.setAtivo(true);

        insumo = insumoRepository.save(insumo);
        return toDTO(insumo);
    }

    /**
     * Busca insumo por ID. Retorna inclusive inativos (ativo = false).
     */
    @Transactional(readOnly = true)
    public InsumoDTO buscarPorId(Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo nao encontrado"));
        return toDTO(insumo);
    }

    /**
     * Lista insumos.
     * aceita ?nome= para busca parcial (ignorando maiusculas/minusculas).
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos (todos) ou ?ativo=false (apenas inativos).
     * Resultado ordenado por nome crescente.
     */
    @Transactional(readOnly = true)
    public List<InsumoDTO> listar(String nome, Boolean ativo) {
        // Por padrao (parametro nao informado) lista apenas insumos ativos.
        // aceita ativo=true (ativos), ativo=false (inativos).
        Boolean filtroAtivo = (ativo == null) ? Boolean.TRUE : ativo;
        List<Insumo> lista = insumoRepository.buscarPorNomeEFiltroAtivo(nome, filtroAtivo);
        return lista.stream().map(this::toDTO).toList();
    }

    /**
     * Atualiza cadastro do insumo, sem modificar o saldo.
     * Unidade de medida e imutavel depois de existir movimentacao.
     * Fornecedor indicado deve estar ativo quando alterado.
     * ativo e obrigatorio e permite reativacao.
     */
    @Transactional
    public InsumoDTO atualizar(Long id, InsumoRequest request) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo nao encontrado"));

        // Unidade imutavel apos existir movimentacao (regra do contrato).
        boolean temMovimentacao = movimentacaoRepository
                .listarComFiltros(insumo.getId(), null, null, null, null, null)
                .isEmpty() == false;
        if (temMovimentacao
                && !insumo.getUnidadeMedida().name().equals(request.getUnidadeMedida())) {
            throw new BusinessException("Unidade de medida nao pode ser alterada apos existir movimentacao");
        }

        if (request.getFornecedorId() != null && !request.getFornecedorId().equals(insumo.getFornecedor().getId())) {
            Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Fornecedor nao encontrado"));
            if (!fornecedor.isAtivo()) {
                throw new BusinessException("Fornecedor inativo nao pode ser atribuido a um insumo");
            }
            insumo.setFornecedor(fornecedor);
        }

        insumo.setNome(request.getNome());
        insumo.setUnidadeMedida(Insumo.UnidadeMedida.valueOf(request.getUnidadeMedida()));
        insumo.setEstoqueMinimo(request.getEstoqueMinimo());
        insumo.setAtivo(request.getAtivo());

        insumo = insumoRepository.save(insumo);
        return toDTO(insumo);
    }

    /**
     * Exclui o insumo se nao houver movimentacoes.
     * Caso contrario, inativa logica (ativo = false) preservando o historico.
     * Retorna 204 sem corpo em ambos os casos.
     */
    @Transactional
    public void excluirOuInativar(Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo nao encontrado"));

        boolean temMovimentacao = movimentacaoRepository
                .listarComFiltros(id, null, null, null, null, null)
                .isEmpty() == false;

        if (temMovimentacao) {
            // Ha historico: so inativa, nunca apaga.
            insumo.setAtivo(false);
            insumoRepository.save(insumo);
        } else {
            // Sem historico: pode excluir definitivamente.
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
