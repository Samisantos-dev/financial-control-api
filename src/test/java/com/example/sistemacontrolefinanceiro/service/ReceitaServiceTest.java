package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReceitaServiceTest {

    private ReceitaRepository receitaRepository;
    private ReceitaService receitaService;

    @BeforeEach
    void setUp() {
        receitaRepository = mock(ReceitaRepository.class);
        receitaService = new ReceitaService(receitaRepository, mock(UsuarioService.class));
    }

    @Test
    void deletarReceitaExistenteDeveRemoverPorId() {
        Long id = 12L;
        when(receitaRepository.existsById(id)).thenReturn(true);

        receitaService.deletarReceita(id);

        verify(receitaRepository).deleteById(id);
    }

    @Test
    void deletarReceitaInexistenteDeveRetornarNaoEncontrado() {
        Long id = 12L;
        when(receitaRepository.existsById(id)).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> receitaService.deletarReceita(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(receitaRepository, never()).deleteById(id);
    }
}
