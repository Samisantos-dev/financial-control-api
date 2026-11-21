package com.example.sistemacontrolefinanceiro.controller;


import com.example.sistemacontrolefinanceiro.model.Receita;
import com.example.sistemacontrolefinanceiro.service.ReceitaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller: recebe as requisições HTTP e devolve as respostas.
// Não tem regra de negócio, só repassa o trabalho para o Categoria.

@RestController
@RequestMapping ("/Receita")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService categoriaService) {
       receitaService = categoriaService;
    }

    @PostMapping ("/Cadastro")
    public Receita cadastrarReceita(@RequestBody Receita receita){
        return receitaService.cadastrarReceita(receita);
    }

}
