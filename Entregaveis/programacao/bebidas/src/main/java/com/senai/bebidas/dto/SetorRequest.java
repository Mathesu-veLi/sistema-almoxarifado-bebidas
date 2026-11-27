package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetorRequest {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    private String descricao;

    /** Obrigatório no PUT; validado no Service. */
    @NotNull(message = "O campo ativo é obrigatório")
    private Boolean ativo;
}
