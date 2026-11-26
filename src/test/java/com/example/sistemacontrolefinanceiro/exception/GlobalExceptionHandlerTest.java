package com.example.sistemacontrolefinanceiro.exception;

import com.example.sistemacontrolefinanceiro.controller.UsuarioController;
import com.example.sistemacontrolefinanceiro.dto.UsuarioRequestDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void senhaComMenosDeOitoCaracteresDeveFalharNaValidacao() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("Maria");
        dto.setEmail("maria@example.com");
        dto.setSenha("1234567");

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Set<jakarta.validation.ConstraintViolation<UsuarioRequestDTO>> violations =
                    validator.validate(dto, UsuarioRequestDTO.CadastroOuAtualizacao.class);

            assertEquals(1, violations.size());
            assertEquals(
                    "A senha deve ter no mínimo 8 caracteres",
                    violations.iterator().next().getMessage());
        }
    }

    @Test
    void errosDeValidacaoDevemRetornarBadRequestComMensagem() throws NoSuchMethodException {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(dto, "usuarioRequestDTO");
        bindingResult.addError(new FieldError(
                "usuarioRequestDTO",
                "senha",
                "1234567",
                false,
                null,
                null,
                "A senha deve ter no mínimo 8 caracteres"));

        MethodParameter parameter = new MethodParameter(
                UsuarioController.class.getMethod("loginUsuario", UsuarioRequestDTO.class), 0);
        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(parameter, bindingResult);

        var response = new GlobalExceptionHandler().tratarDadosInvalidos(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(
                "senha: A senha deve ter no mínimo 8 caracteres",
                response.getBody());
    }
}
