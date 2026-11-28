package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.model.Receita;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import com.example.sistemacontrolefinanceiro.repository.ReceitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReceitaService {

    private UsuarioService usuarioService;
    private ReceitaRepository receitaRepository;


    public ReceitaService(ReceitaRepository receitaRepository, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.receitaRepository = receitaRepository;

    }

    public Receita cadastrarReceita(Receita receita) {

        Usuario usuario = usuarioService.listarUsuarioPorId(receita.getUsuario().getId());
        receita.setUsuario(usuario);

        return receitaRepository.save(receita);
    }

    public void deletarReceita(Long id) {
        if (!receitaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada");
        }
        receitaRepository.deleteById(id);
    }
}




