package com.example.sistemacontrolefinanceiro.controller;

import com.example.sistemacontrolefinanceiro.dto.UsuarioRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.UsuarioResponseDTO;
import com.example.sistemacontrolefinanceiro.mapper.UsuarioMapper;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import com.example.sistemacontrolefinanceiro.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {

    private UsuarioService usuarioService;
    private UsuarioMapper usuarioMapper;
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = mock(UsuarioService.class);
        usuarioMapper = mock(UsuarioMapper.class);
        usuarioController = new UsuarioController(usuarioService, usuarioMapper);
    }

    @Test
    void cadastrarUsuarioDeveConverterSalvarERetornarDto() {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        Usuario usuario = new Usuario();
        UsuarioResponseDTO response = new UsuarioResponseDTO();

        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(usuarioService.cadastrarUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertSame(response, usuarioController.cadastrarUsuario(request));

        verify(usuarioMapper).toEntity(request);
        verify(usuarioService).cadastrarUsuario(usuario);
        verify(usuarioMapper).toResponse(usuario);
    }

    @Test
    void atualizarUsuarioDeveDefinirIdSalvarERetornarDto() {
        Long id = 12L;
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        Usuario usuario = new Usuario();
        UsuarioResponseDTO response = new UsuarioResponseDTO();

        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(usuarioService.atualizarUsuario(id, usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertSame(response, usuarioController.atualizarUsuario(id, request));
        assertEquals(id, usuario.getId());

        verify(usuarioService).atualizarUsuario(id, usuario);
    }

    @Test
    void loginUsuarioDeveValidarUsuarioERetornarDto() {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        Usuario usuario = new Usuario();
        UsuarioResponseDTO response = new UsuarioResponseDTO();

        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(usuarioService.loginUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertSame(response, usuarioController.loginUsuario(request));

        verify(usuarioService).loginUsuario(usuario);
        verify(usuarioMapper).toResponse(usuario);
    }

    @Test
    void buscarUsuarioDeveConsultarPorIdERetornarDto() {
        Long id = 12L;
        Usuario usuario = new Usuario();
        UsuarioResponseDTO response = new UsuarioResponseDTO();

        when(usuarioService.listarUsuarioPorId(id)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertSame(response, usuarioController.buscarUsuarioPorId(id));

        verify(usuarioService).listarUsuarioPorId(id);
        verify(usuarioMapper).toResponse(usuario);
    }

    @Test
    void deletarUsuarioDeveDelegarAoService() {
        Long id = 12L;

        usuarioController.deletarUsuario(id);

        verify(usuarioService).deletarUsuario(id);
    }
}
