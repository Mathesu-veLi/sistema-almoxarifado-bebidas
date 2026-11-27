package com.senai.bebidas.exception;

/**
 * Erro de validacao de entrada (HTTP 400).
 * Diferente de BusinessException, que representa violacao de regra/estado (HTTP 409).
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
