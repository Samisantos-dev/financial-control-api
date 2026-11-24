package com.example.sistemacontrolefinanceiro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    @NotNull(groups = CadastroOuAtualizacao.class)
    @NotBlank(groups = CadastroOuAtualizacao.class)
    private String nome;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String senha;

    public interface CadastroOuAtualizacao extends Default {
    }
}
