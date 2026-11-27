package com.senai.bebidas.exception;

/**
 * Excecao de validacao de entrada (400).
 * Usada para regras de entrada do cliente que violam o contrato,
 * diferenciado das excecoes de negocio (409), como estoque insuficiente.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
