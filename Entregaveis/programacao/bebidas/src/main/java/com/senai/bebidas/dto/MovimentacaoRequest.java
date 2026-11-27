package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de entrada para registrar uma Movimentacao.
 * Nao recebe usuarioId (vem do JWT) nem saldoAnterior/saldoAtual (calculados pelo servidor).
 */
@Getter
@Setter
@NoArgsConstructor
public class MovimentacaoRequest {

    @NotNull(message = "O campo insumoId e obrigatorio")
    private Long insumoId;

    @NotNull(message = "O campo tipo e obrigatorio")
    private String tipo;

    @NotNull(message = "O campo quantidade e obrigatorio")
    @Positive(message = "A quantidade deve ser maior que zero")
    private BigDecimal quantidade;

    private Long setorId;

    @NotNull(message = "O campo dataMovimentacao e obrigatorio")
    private LocalDate dataMovimentacao;

    private String lote;

    private LocalDate validade;
}
