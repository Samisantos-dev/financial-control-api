package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.model.Categoria;
import com.example.sistemacontrolefinanceiro.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {

    private UsuarioService usuarioService;
    private CategoriaRepository categoriaRepository;


    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioService usuarioService ) {
        this.usuarioService = usuarioService;
        this.categoriaRepository = categoriaRepository;
    }

        public Categoria cadastrarCategoria(Categoria categoria) {

            if (categoria.getNome() == null || categoria.getNome().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O nome da categoria deve ser informado"
                );
            }

            if (categoria.getUsuario() == null || categoria.getUsuario().getId() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário deve ser informado"
                );
            }

            usuarioService.listarUsuarioPorId(categoria.getUsuario().getId());

            if (categoriaRepository.existsByNomeAndUsuarioId(
                    categoria.getNome(), categoria.getUsuario().getId())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Essa categoria já está cadastrada para esse usuário"
                );
            }

            return categoriaRepository.save(categoria);
        }

        public List<Categoria> listarCategoriasPorUsuario(Long usuarioId) {

            usuarioService.listarUsuarioPorId(usuarioId);

            return categoriaRepository.findByUsuarioId(usuarioId);
        }

        public Categoria buscarCategoriaPorId(Long id) {

            return categoriaRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Categoria não encontrada"
                    ));
        }

        public Categoria atualizarCategoria(Categoria categoria) {

            if (categoria.getId() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O ID da categoria deve ser informado"
                );
            }

            Categoria categoriaExistente = buscarCategoriaPorId(categoria.getId());

            if (categoria.getNome() == null || categoria.getNome().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O nome da categoria deve ser informado"
                );
            }

            categoriaExistente.setNome(categoria.getNome());

            return categoriaRepository.save(categoriaExistente);
        }

        public void excluirCategoria(Long id) {

            Categoria categoria = buscarCategoriaPorId(id);

            categoriaRepository.delete(categoria);
        }
    }




