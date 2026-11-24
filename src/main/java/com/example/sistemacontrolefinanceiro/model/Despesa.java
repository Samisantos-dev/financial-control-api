package com.example.sistemacontrolefinanceiro.model;

import com.example.sistemacontrolefinanceiro.enums.CategoriaEnum;
import com.example.sistemacontrolefinanceiro.enums.FormaPagamentoEnum;
import com.example.sistemacontrolefinanceiro.enums.StatusPagamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private LocalDate dataVencimento;
    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoriaEnum;
    @Enumerated(EnumType.STRING)
    private FormaPagamentoEnum formaPagamentoEnum;
    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum statusPagamentoEnum;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;




}