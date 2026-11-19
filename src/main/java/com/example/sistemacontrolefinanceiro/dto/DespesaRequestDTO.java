package com.example.sistemacontrolefinanceiro.dto;

import com.example.sistemacontrolefinanceiro.enums.CategoriaEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class DespesaRequestDTO {

    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private Long usuarioId;
    private CategoriaEnum categoriaEnum;

}
