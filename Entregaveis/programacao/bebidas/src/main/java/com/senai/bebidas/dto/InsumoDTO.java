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
public class InsumoDTO {

    private Long id;
    private String nome;
    private String unidadeMedida;
    private BigDecimal quantidadeAtual;
    private BigDecimal estoqueMinimo;
    private Long fornecedorId;
    private boolean ativo;
}
