package com.example.sistemacontrolefinanceiro.exception;

public class IdNaoExisteException extends RuntimeException {
    public IdNaoExisteException(String message) {
        super("Id Nao Existente");
    }
}
