package com.example.sistemacontrolefinanceiro.dto;

import com.example.sistemacontrolefinanceiro.enums.CategoriaEnum;
import com.example.sistemacontrolefinanceiro.enums.FormaPagamentoEnum;
import com.example.sistemacontrolefinanceiro.enums.StatusPagamentoEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class DespesaRequestDTO {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal valor;

    @NotBlank
    private String descricao;

    @NotNull
    private LocalDate data;

    @NotNull
    private LocalDate dataVencimento;

    @NotNull
    private CategoriaEnum categoriaEnum;

    @NotNull
    private FormaPagamentoEnum formaPagamentoEnum;

    private StatusPagamentoEnum statusPagamentoEnum;

    @NotNull
    private Long usuarioId;   // novo

}
