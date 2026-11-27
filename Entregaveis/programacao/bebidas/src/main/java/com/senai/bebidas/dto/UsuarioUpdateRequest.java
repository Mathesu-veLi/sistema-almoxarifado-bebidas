package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioUpdateRequest {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo login é obrigatório")
    private String login;

    /** Obrigatório no PUT (contrato); permite reativação quando true. */
    @NotNull(message = "O campo ativo é obrigatório")
    private Boolean ativo;
}
