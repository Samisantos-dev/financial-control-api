package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.exception.SenhaIncorretaException;
import com.example.sistemacontrolefinanceiro.exception.UsuarioJaExisteException;
import com.example.sistemacontrolefinanceiro.exception.UsuarioNaoEncontradoException;
import com.example.sistemacontrolefinanceiro.mapper.UsuarioMapper;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import com.example.sistemacontrolefinanceiro.repository.UsuarioRepository;
import jakarta.persistence.Id;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Camada de serviço de usuários.
 *
 * <p>Concentra as regras de negócio, como impedir o cadastro de email
 * duplicado e validar a senha no login.
 */
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        boolean emailJaExistente = usuarioRepository.existsByEmail(usuario.getEmail());
        if (emailJaExistente) {
            throw new UsuarioJaExisteException("E-mail já cadastrado!");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha não pode ser vazia!");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        boolean usuarioExiste = usuarioRepository.existsById(id);
        if (!usuarioExiste) {
            throw new UsuarioNaoEncontradoException("ID não encontrado!");
        }
        usuario.setId(id);

        return usuarioRepository.save(usuario);
    }

    // Criar um boolean para validar a entrada de login
    public Usuario loginUsuario(Usuario usuario) {

        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEncontrado == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado!");
        } else if (usuarioEncontrado.getSenha().equals(usuario.getSenha())) {
            return usuarioEncontrado;
        } else {
            throw new SenhaIncorretaException("Senha incorreta!");
        }
    }

    public void deletarUsuario(Long id) {
        boolean usuarioExiste = usuarioRepository.existsById(id);
        if (!usuarioExiste) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado!");
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario listarUsuarioPorId(Long id) {
        boolean usuarioExiste = usuarioRepository.existsById(id);
        if (!usuarioExiste) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado!");
        }
        return usuarioRepository.findById(id).get();
    }

}