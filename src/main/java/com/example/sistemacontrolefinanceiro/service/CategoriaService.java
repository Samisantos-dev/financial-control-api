package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.enums.CategoriaEnum;
import com.example.sistemacontrolefinanceiro.exception.CategoriaNaoExisteException;
import com.example.sistemacontrolefinanceiro.model.Categoria;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import com.example.sistemacontrolefinanceiro.repository.CategoriaRepository;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;
    private UsuarioService usuarioService;


    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria CadastrarCategoria(Categoria categoria) {

        Long usuarioId = categoria.getUsuario().getId();

        Usuario usuario = usuarioService.listarUsuarioPorId(usuarioId);

        categoria.setUsuario(usuario);

        CategoriaEnum categoriaNome = categoria.getNome();

        if (categoriaNome == null) {
            throw new CategoriaNaoExisteException("Categoria não encontrada!");
        }

        return categoriaRepository.save(categoria);
    }
    }


