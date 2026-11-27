package com.senai.bebidas.service;

import com.senai.bebidas.dto.MovimentacaoDTO;
import com.senai.bebidas.dto.MovimentacaoRequest;
import com.senai.bebidas.entity.Insumo;
import com.senai.bebidas.entity.Movimentacao;
import com.senai.bebidas.entity.Setor;
import com.senai.bebidas.entity.Usuario;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.repository.InsumoRepository;
import com.senai.bebidas.repository.MovimentacaoRepository;
import com.senai.bebidas.repository.SetorRepository;
import com.senai.bebidas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Service de Movimentacao.
 * Aplica as regras de negocio de entrada e saida de insumos no estoque.
 * Historico e saldo sao atualizados em uma unica transacao (RN-04).
 */
@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final InsumoRepository insumoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final SetorRepository setorRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Registra uma entrada ou saida de insumo.
     * O usuarioId vem do JWT (nao e enviado pelo cliente).
     * Tudo acontece dentro de uma transacao: se algo falhar, nada e gravado.
     */
    @Transactional
    public MovimentacaoDTO registrar(MovimentacaoRequest request, Long usuarioIdAutenticado) {

        // 1. Busca o insumo com lock (impedir duas alteracoes simultaneas no mesmo saldo).
        Insumo insumo = insumoRepository.buscarParaAtualizacao(request.getInsumoId());
        if (insumo == null) {
            throw new EntityNotFoundException("Insumo nao encontrado");
        }

        // 2. Valida o tipo (somente ENTRADA ou SAIDA).
        String tipo = request.getTipo();
        if (!"ENTRADA".equals(tipo) && !"SAIDA".equals(tipo)) {
            throw new BusinessException("Tipo deve ser ENTRADA ou SAIDA");
        }

        // 3. Valida a data operacional (nao pode ser futura) - RN-13.
        LocalDate dataMov = request.getDataMovimentacao();
        if (dataMov != null && dataMov.isAfter(LocalDate.now())) {
            throw new ValidationException("Data operacional nao pode ser futura (RN-13)");
        }

        // 4. A quantidade e validada pelo @Positive no request (deve ser estritamente positiva - RN-02).
        BigDecimal quantidade = request.getQuantidade();

        // 5. Se o insumo e UN, nao aceita quantidade fracionaria - RN-02.
        if (insumo.getUnidadeMedida() == Insumo.UnidadeMedida.UN
                && quantidade.stripTrailingZeros().scale() > 0) {
            throw new BusinessException("Insumo UN nao aceita quantidade fracionaria (RN-02)");
        }

        Setor setor = null;

        // 6. Regras especificas de SAIDA - RN-06.
        if ("SAIDA".equals(tipo)) {
            if (request.getSetorId() == null) {
                throw new BusinessException("SAIDA exige setor (RN-06)");
            }
            setor = setorRepository.findById(request.getSetorId())
                    .orElseThrow(() -> new EntityNotFoundException("Setor nao encontrado"));
            if (!setor.isAtivo()) {
                throw new BusinessException("Setor inativo nao permitido para SAIDA (RN-06)");
            }
            // SAIDA nao recebe lote nem validade.
            if (request.getLote() != null || request.getValidade() != null) {
                throw new BusinessException("SAIDA nao recebe lote/validade (RN-06)");
            }
        }

        // 7. Valida a validade (nao pode ser anterior a data da movimentacao).
        if (request.getValidade() != null && dataMov != null
                && request.getValidade().isBefore(dataMov)) {
            throw new BusinessException("Validade nao pode ser anterior a data da movimentacao");
        }

        // 8. Calcula o saldo anterior e o saldo atual.
        BigDecimal saldoAnterior = insumo.getQuantidadeAtual();
        BigDecimal saldoAtual;

        if ("ENTRADA".equals(tipo)) {
            // Entrada aumenta o saldo.
            saldoAtual = saldoAnterior.add(quantidade);
        } else {
            // Saida diminui o saldo.
            saldoAtual = saldoAnterior.subtract(quantidade);
            if (saldoAtual.compareTo(BigDecimal.ZERO) < 0) {
                // Saldo negativo nao e permitido - RN-03.
                throw new BusinessException("Quantidade solicitada superior ao estoque disponivel");
            }
        }

        // 9. Atualiza o saldo do insumo no banco.
        insumo.setQuantidadeAtual(saldoAtual);
        insumoRepository.save(insumo);

        // 10. Busca o usuario autenticado (quem fez a movimentacao).
        Usuario usuario = usuarioRepository.findById(usuarioIdAutenticado)
                .orElseThrow(() -> new EntityNotFoundException("Usuario autenticado nao encontrado"));

        // 11. Cria e grava a movimentacao com data de registro gerada pelo servidor.
        Movimentacao mov = new Movimentacao();
        mov.setInsumo(insumo);
        mov.setTipo(Movimentacao.Tipo.valueOf(tipo));
        mov.setQuantidade(quantidade);
        mov.setSetor(setor);
        mov.setUsuario(usuario);
        mov.setDataMovimentacao(dataMov);
        mov.setDataRegistro(OffsetDateTime.now()); // data/hora real com fuso horario
        mov.setLote(request.getLote());
        mov.setValidade(request.getValidade());
        mov.setSaldoAnterior(saldoAnterior);
        mov.setSaldoAtual(saldoAtual);

        mov = movimentacaoRepository.save(mov);

        return toDTO(mov);
    }

    /**
     * Lista o historico de movimentacoes com filtros opcionais.
     * Filtros: insumoId, setorId, usuarioId, tipo, dataInicio, dataFim.
     * Ordenacao: dataRegistro decrescente e, no empate, id decrescente.
     */
    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> listar(Long insumoId, Long setorId, Long usuarioId,
                                        String tipo, LocalDate dataInicio, LocalDate dataFim) {
        List<Movimentacao> lista = movimentacaoRepository.listarComFiltros(
                insumoId, setorId, usuarioId, tipo, dataInicio, dataFim);
        return lista.stream().map(this::toDTO).toList();
    }

    /**
     * Busca uma movimentacao por ID.
     */
    @Transactional(readOnly = true)
    public MovimentacaoDTO buscarPorId(Long id) {
        Movimentacao mov = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimentacao nao encontrada"));
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
