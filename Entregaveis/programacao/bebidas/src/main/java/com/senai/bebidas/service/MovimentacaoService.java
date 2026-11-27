package com.senai.bebidas.service;

import com.senai.bebidas.dto.MovimentacaoDTO;
import com.senai.bebidas.dto.MovimentacaoRequest;
import com.senai.bebidas.entity.Insumo;
import com.senai.bebidas.entity.Movimentacao;
import com.senai.bebidas.entity.Setor;
import com.senai.bebidas.entity.Usuario;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.repository.InsumoRepository;
import com.senai.bebidas.repository.MovimentacaoRepository;
import com.senai.bebidas.repository.SetorRepository;
import com.senai.bebidas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final InsumoRepository insumoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final SetorRepository setorRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public MovimentacaoDTO registrar(MovimentacaoRequest request, Long usuarioIdAutenticado) {
        Insumo insumo = insumoRepository.findByIdForUpdate(request.getInsumoId());
        if (insumo == null) {
            throw new EntityNotFoundException("Insumo não encontrado");
        }

        // CT13: insumo inativo não pode ser movimentado
        if (!insumo.isAtivo()) {
            throw new BusinessException("Insumo inativo não pode ser movimentado");
        }

        String tipo = request.getTipo();
        if (!"ENTRADA".equals(tipo) && !"SAIDA".equals(tipo)) {
            throw new BusinessException("Tipo deve ser ENTRADA ou SAIDA");
        }

        LocalDate dataMov = request.getDataMovimentacao();
        if (dataMov != null && dataMov.isAfter(LocalDate.now())) {
            throw new BusinessException("Data operacional não pode ser futura (RN-13)");
        }

        BigDecimal quantidade = request.getQuantidade();
        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Quantidade deve ser estritamente positiva (RN-02)");
        }

        // RN-02: UN não aceita quantidade fracionária
        if (insumo.getUnidadeMedida() == Insumo.UnidadeMedida.UN
                && quantidade.stripTrailingZeros().scale() > 0) {
            throw new BusinessException("Insumo UN não aceita quantidade fracionária (RN-02)");
        }

        Setor setor = null;
        if ("SAIDA".equals(tipo)) {
            if (request.getSetorId() == null) {
                throw new BusinessException("SAIDA exige setor (RN-06)");
            }
            setor = setorRepository.findById(request.getSetorId())
                    .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
            if (!setor.isAtivo()) {
                throw new BusinessException("Setor inativo não permitido para SAIDA (RN-06)");
            }
            if (request.getLote() != null || request.getValidade() != null) {
                throw new BusinessException("SAIDA não recebe lote/validade (RN-06)");
            }
        }

        if (request.getValidade() != null && dataMov != null
                && request.getValidade().isBefore(dataMov)) {
            throw new BusinessException("Validade não pode ser anterior à data da movimentação");
        }

        BigDecimal saldoAnterior = insumo.getQuantidadeAtual();
        BigDecimal saldoAtual;
        if ("ENTRADA".equals(tipo)) {
            saldoAtual = saldoAnterior.add(quantidade);
        } else {
            saldoAtual = saldoAnterior.subtract(quantidade);
            if (saldoAtual.compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Quantidade solicitada superior ao estoque disponível");
            }
        }

        insumo.setQuantidadeAtual(saldoAtual);
        insumoRepository.save(insumo);

        Usuario usuario = usuarioRepository.findById(usuarioIdAutenticado)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        Movimentacao mov = new Movimentacao();
        mov.setInsumo(insumo);
        mov.setTipo(Movimentacao.Tipo.valueOf(tipo));
        mov.setQuantidade(quantidade);
        mov.setSetor(setor);
        mov.setUsuario(usuario);
        mov.setDataMovimentacao(dataMov);
        mov.setDataRegistro(OffsetDateTime.now(ZoneId.of("America/Sao_Paulo")));
        mov.setLote(request.getLote());
        mov.setValidade(request.getValidade());
        mov.setSaldoAnterior(saldoAnterior);
        mov.setSaldoAtual(saldoAtual);
        mov = movimentacaoRepository.save(mov);

        return toDTO(mov);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> listar(Long insumoId, Long setorId, Long usuarioId, String tipo,
                                        LocalDate dataInicio, LocalDate dataFim) {
        Specification<Movimentacao> spec = (root, query, cb) -> cb.conjunction();

        if (insumoId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("insumo").get("id"), insumoId));
        }
        if (setorId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("setor").get("id"), setorId));
        }
        if (usuarioId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("usuario").get("id"), usuarioId));
        }
        if (tipo != null && !tipo.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("tipo"), Movimentacao.Tipo.valueOf(tipo)));
        }
        if (dataInicio != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataMovimentacao"), dataInicio));
        }
        if (dataFim != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dataMovimentacao"), dataFim));
        }

        // Ordenação: dataRegistro decrescente, id decrescente (contrato)
        List<Movimentacao> lista = movimentacaoRepository.findAll(spec,
                org.springframework.data.domain.Sort.by(
                        org.springframework.data.domain.Sort.Order.desc("dataRegistro"),
                        org.springframework.data.domain.Sort.Order.desc("id")
                ));
        return lista.stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public MovimentacaoDTO buscarPorId(Long id) {
        Movimentacao mov = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada"));
        return toDTO(mov);
    }

    private MovimentacaoDTO toDTO(Movimentacao m) {
        MovimentacaoDTO dto = new MovimentacaoDTO();
        dto.setId(m.getId());
        dto.setInsumoId(m.getInsumo().getId());
        dto.setTipo(m.getTipo().name());
        dto.setQuantidade(m.getQuantidade());
        dto.setSetorId(m.getSetor() != null ? m.getSetor().getId() : null);
        dto.setUsuarioId(m.getUsuario().getId());
        dto.setDataMovimentacao(m.getDataMovimentacao());
        dto.setDataRegistro(m.getDataRegistro());
        dto.setLote(m.getLote());
        dto.setValidade(m.getValidade());
        dto.setSaldoAnterior(m.getSaldoAnterior());
        dto.setSaldoAtual(m.getSaldoAtual());
        return dto;
    }
}
