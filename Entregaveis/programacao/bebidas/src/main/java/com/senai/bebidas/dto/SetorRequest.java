package com.senai.bebidas.dto;

import jakarta.validation.constraints.NotBlank;
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

    private boolean ativo = true;
}
