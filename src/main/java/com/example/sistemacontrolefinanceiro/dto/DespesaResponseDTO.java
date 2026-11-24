package com.example.sistemacontrolefinanceiro.dto;

import com.example.sistemacontrolefinanceiro.enums.CategoriaEnum;
import com.example.sistemacontrolefinanceiro.enums.FormaPagamentoEnum;
import com.example.sistemacontrolefinanceiro.enums.StatusPagamentoEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DespesaResponseDTO {

    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private LocalDate dataVencimento;
    private CategoriaEnum categoriaEnum;
    private FormaPagamentoEnum formaPagamentoEnum;
    private StatusPagamentoEnum statusPagamentoEnum;
    private long id;
}
