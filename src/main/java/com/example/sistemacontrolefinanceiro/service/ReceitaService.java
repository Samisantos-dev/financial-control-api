package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.model.Receita;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import com.example.sistemacontrolefinanceiro.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceitaService {

    private UsuarioService usuarioService;
    private ReceitaRepository receitaRepository;


    public ReceitaService(ReceitaRepository receitaRepository, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.receitaRepository = receitaRepository;
        this.usuarioService = usuarioService;
    }

    public Receita cadastrarReceita(Receita receita) {

        Usuario usuario = usuarioService.listarUsuarioPorId(receita.getUsuario().getId());
        receita.setUsuario(usuario);

        return receitaRepository.save(receita);
    }
}




