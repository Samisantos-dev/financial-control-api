package com.example.sistemacontrolefinanceiro.controller;

import com.example.sistemacontrolefinanceiro.dto.UsuarioRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.UsuarioResponseDTO;
import com.example.sistemacontrolefinanceiro.exception.GlobalExceptionHandler;
import com.example.sistemacontrolefinanceiro.mapper.UsuarioMapper;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import com.example.sistemacontrolefinanceiro.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class UsuarioControllerRequestTest {

    private UsuarioService usuarioService;
    private UsuarioMapper usuarioMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        usuarioService = mock(UsuarioService.class);
        usuarioMapper = mock(UsuarioMapper.class);
        mockMvc = standaloneSetup(new UsuarioController(usuarioService, usuarioMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void postCadastrarDeveRetornarUsuarioCriado() throws Exception {
        UsuarioRequestDTO request = usuarioRequest("Maria", "maria@example.com", "senha123");
        Usuario usuario = usuario("Maria", "maria@example.com");
        UsuarioResponseDTO response = usuarioResponse(1L, "Maria", "maria@example.com");

        when(usuarioMapper.toEntity(any(UsuarioRequestDTO.class))).thenReturn(usuario);
        when(usuarioService.cadastrarUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.email").value("maria@example.com"));

        verify(usuarioService).cadastrarUsuario(usuario);
    }

    @Test
    void postCadastrarComDadosInvalidosDeveRetornarBadRequest() throws Exception {
        String json = """
                {
                  "nome": "",
                  "email": "email-invalido",
                  "senha": "123"
                }
                """;

        mockMvc.perform(post("/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("A senha deve ter no mínimo 8 caracteres")));
    }

    @Test
    void putAtualizarDeveRetornarUsuarioAtualizado() throws Exception {
        UsuarioRequestDTO request = usuarioRequest("Maria Silva", "maria@example.com", "senha123");
        Usuario usuario = usuario("Maria Silva", "maria@example.com");
        usuario.setId(12L);
        UsuarioResponseDTO response = usuarioResponse(12L, "Maria Silva", "maria@example.com");

        when(usuarioMapper.toEntity(any(UsuarioRequestDTO.class))).thenReturn(usuario);
        when(usuarioService.atualizarUsuario(12L, usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        mockMvc.perform(put("/usuarios/atualizar/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12))
                .andExpect(jsonPath("$.nome").value("Maria Silva"));

        verify(usuarioService).atualizarUsuario(12L, usuario);
    }

    @Test
    void postLoginDeveRetornarUsuarioAutenticado() throws Exception {
        UsuarioRequestDTO request = usuarioRequest(null, "maria@example.com", "senha123");
        Usuario usuario = usuario(null, "maria@example.com");
        UsuarioResponseDTO response = usuarioResponse(1L, "Maria", "maria@example.com");

        when(usuarioMapper.toEntity(any(UsuarioRequestDTO.class))).thenReturn(usuario);
        when(usuarioService.loginUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria@example.com"));

        verify(usuarioService).loginUsuario(usuario);
    }

    @Test
    void postLoginComSenhaCurtaDeveRetornarBadRequest() throws Exception {
        String json = """
                {
                  "email": "maria@example.com",
                  "senha": "123"
                }
                """;

        mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("A senha deve ter no mínimo 8 caracteres")));
    }

    @Test
    void getBuscarPorIdDeveRetornarUsuario() throws Exception {
        Usuario usuario = usuario("Maria", "maria@example.com");
        usuario.setId(12L);
        UsuarioResponseDTO response = usuarioResponse(12L, "Maria", "maria@example.com");

        when(usuarioService.listarUsuarioPorId(12L)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        mockMvc.perform(get("/usuarios/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12))
                .andExpect(jsonPath("$.nome").value("Maria"));

        verify(usuarioService).listarUsuarioPorId(12L);
    }

    @Test
    void deleteDeletarDeveRetornarOk() throws Exception {
        mockMvc.perform(delete("/usuarios/deletar/12"))
                .andExpect(status().isOk());

        verify(usuarioService).deletarUsuario(12L);
    }

    private UsuarioRequestDTO usuarioRequest(String nome, String email, String senha) {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome(nome);
        dto.setEmail(email);
        dto.setSenha(senha);
        return dto;
    }

    private Usuario usuario(String nome, String email) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha("senha123");
        return usuario;
    }

    private UsuarioResponseDTO usuarioResponse(Long id, String nome, String email) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(id);
        response.setNome(nome);
        response.setEmail(email);
        return response;
    }
}
