package com.example.sistemacontrolefinanceiro.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReceitaRequestDTO {

    @NotBlank
    private String descricao;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal valor;

    @NotNull
    private LocalDate data;

    @NotNull
    @Positive
    private Long usuarioId;
}
