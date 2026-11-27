package com.senai.bebidas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
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
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataRegistro;
    
    private String lote;
    private LocalDate validade;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoAtual;
}
