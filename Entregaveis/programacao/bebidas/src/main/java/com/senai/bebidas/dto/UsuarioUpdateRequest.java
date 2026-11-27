package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de entrada para atualizacao de Usuario.
 * Nao permite alterar senha. O campo ativo e obrigatorio e permite reativacao.
 */
@Getter
@Setter
@NoArgsConstructor
public class UsuarioUpdateRequest {

    @NotBlank(message = "O campo nome e obrigatorio")
    private String nome;

    @NotBlank(message = "O campo login e obrigatorio")
    private String login;

    @NotNull(message = "O campo ativo e obrigatorio")
    private Boolean ativo;
}
