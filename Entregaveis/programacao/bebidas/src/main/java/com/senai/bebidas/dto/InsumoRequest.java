package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsumoRequest {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo unidadeMedida é obrigatório")
    private String unidadeMedida;

    @NotNull(message = "O campo estoqueMinimo é obrigatório")
    private BigDecimal estoqueMinimo;

    @NotNull(message = "O campo fornecedorId é obrigatório")
    private Long fornecedorId;

    private boolean ativo = true;
}
