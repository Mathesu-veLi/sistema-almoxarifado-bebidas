package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovimentacaoRequest {

    @NotNull(message = "O campo insumoId é obrigatório")
    private Long insumoId;

    @NotNull(message = "O campo tipo é obrigatório")
    private String tipo;

    @NotNull(message = "O campo quantidade é obrigatório")
    private BigDecimal quantidade;

    private Long setorId;

    @NotNull(message = "O campo dataMovimentacao é obrigatório")
    private LocalDate dataMovimentacao;

    private String lote;

    private LocalDate validade;
}
