package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de entrada para cadastro/atualizacao de Insumo.
 * Nao recebe quantidadeAtual (saldo comeca sempre em zero).
 */
@Getter
@Setter
@NoArgsConstructor
public class InsumoRequest {

    @NotBlank(message = "O campo nome e obrigatorio")
    private String nome;

    @NotBlank(message = "O campo unidadeMedida e obrigatorio")
    private String unidadeMedida;

    @NotNull(message = "O campo estoqueMinimo e obrigatorio")
    private BigDecimal estoqueMinimo;

    @NotNull(message = "O campo fornecedorId e obrigatorio")
    private Long fornecedorId;

    @NotNull(message = "O campo ativo e obrigatorio")
    private Boolean ativo;
}
