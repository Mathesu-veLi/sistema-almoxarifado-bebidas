package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de entrada para cadastro/atualizacao de Setor.
 * Nome e obrigatorio.
 */
@Getter
@Setter
@NoArgsConstructor
public class SetorRequest {

    @NotBlank(message = "O campo nome e obrigatorio")
    private String nome;

    private String descricao;

    @NotNull(message = "O campo ativo e obrigatorio")
    private Boolean ativo;
}
