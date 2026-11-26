package com.example.sistemacontrolefinanceiro.controller;

import com.example.sistemacontrolefinanceiro.dto.UsuarioRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.UsuarioResponseDTO;
import com.example.sistemacontrolefinanceiro.mapper.UsuarioMapper;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import com.example.sistemacontrolefinanceiro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// Controlador responsável por receber as requisições HTTP e delegar as operações ao serviço.
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping("/cadastrar")
    public UsuarioResponseDTO cadastrarUsuario(
            @Validated(UsuarioRequestDTO.CadastroOuAtualizacao.class) @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario salvo = usuarioService.cadastrarUsuario(usuario);

        return usuarioMapper.toResponse(salvo);
    }

    @PutMapping("/atualizar/{id}")
    public UsuarioResponseDTO atualizarUsuario(@PathVariable Long id,
            @Validated(UsuarioRequestDTO.CadastroOuAtualizacao.class) @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setId(id);

        Usuario atualizado = usuarioService.atualizarUsuario(id, usuario);

        return usuarioMapper.toResponse(atualizado);
    }

    @PostMapping("/login")
    public UsuarioResponseDTO loginUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario login = usuarioService.loginUsuario(usuario);

        return usuarioMapper.toResponse(login);
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.listarUsuarioPorId(id);
        return usuarioMapper.toResponse(usuario);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }
}
