package com.senai.bebidas.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertaDTO {

    private Long insumoId;
    private String nome;
    private BigDecimal quantidadeAtual;
    private BigDecimal estoqueMinimo;
    private String situacao = "CRITICO";
}
