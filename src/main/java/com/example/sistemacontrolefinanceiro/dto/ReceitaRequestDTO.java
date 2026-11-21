package com.example.sistemacontrolefinanceiro.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReceitaRequestDTO {

    private long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
}
