package com.senai.bebidas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {

    private Long id;
    private Long insumoId;
    private String tipo;
    private BigDecimal quantidade;
    private Long setorId;
    private Long usuarioId;
    private LocalDate dataMovimentacao;
    private LocalDateTime dataRegistro;
    private String lote;
    private LocalDate validade;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoAtual;
}
