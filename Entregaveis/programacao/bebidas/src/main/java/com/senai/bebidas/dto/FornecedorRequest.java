package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de entrada para cadastro/atualizacao de Fornecedor.
 * Nome e CNPJ sao obrigatorios. CNPJ com 14 digitos.
 */
@Getter
@Setter
@NoArgsConstructor
public class FornecedorRequest {

    @NotBlank(message = "O campo nome e obrigatorio")
    private String nome;

    @NotBlank(message = "O campo CNPJ e obrigatorio")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 digitos")
    private String cnpj;

    private String telefone;

    @NotNull(message = "O campo ativo e obrigatorio")
    private Boolean ativo;
}
