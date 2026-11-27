package com.senai.bebidas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "UN|KG|L", message = "A unidadeMedida deve ser UN, KG ou L")
    private String unidadeMedida;

    @NotNull(message = "O campo estoqueMinimo é obrigatório")
    @DecimalMin(value = "0.0", message = "O estoqueMinimo não pode ser negativo")
    @Digits(integer = 12, fraction = 3, message = "O estoqueMinimo deve ter no máximo 3 casas decimais")
    private BigDecimal estoqueMinimo;

    @NotNull(message = "O campo fornecedorId é obrigatório")
    private Long fornecedorId;

    /** Obrigatório no PUT; validado no Service. */
    @NotNull(message = "O campo ativo é obrigatório")
    private Boolean ativo;
}
