package com.example.sistemacontrolefinanceiro.controller;


import com.example.sistemacontrolefinanceiro.dto.ReceitaRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.ReceitaResponseDTO;
import com.example.sistemacontrolefinanceiro.mapper.ReceitaMapper;
import com.example.sistemacontrolefinanceiro.model.Receita;
import com.example.sistemacontrolefinanceiro.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller: recebe as requisições HTTP e devolve as respostas.
// Não tem regra de negócio, só repassa o trabalho para o Categoria.

@RestController
@RequestMapping ("/receita")
public class ReceitaController {

    private final ReceitaService receitaService;
    private final ReceitaMapper receitaMapper;

    public ReceitaController(ReceitaService receitaService , ReceitaMapper receitaMapper) {
      this.receitaService = receitaService;
      this.receitaMapper = receitaMapper;
    }

    @PostMapping ("/salvar")
    public ReceitaResponseDTO cadastrarReceita(@Valid @RequestBody ReceitaRequestDTO dto){
        Receita receita = receitaMapper.toEntity(dto);
        Receita salvar = receitaService.cadastrarReceita(receita);

        return receitaMapper.toResponse(salvar);
    }
}
