package com.senai.bebidas.util;

import com.senai.bebidas.exception.ValidationException;

/**
 * Utilitário para interpretar o parâmetro ?ativo das listagens.
 * Valores válidos: "true", "false", "todos" (case-insensitive).
 * null/blank = apenas ativos (padrão do contrato).
 * Lança ValidationException (HTTP 400) para valores inválidos.
 */
public class FiltroAtivo {

    public static Boolean interpretar(String ativo) {
        if (ativo == null || ativo.isBlank()) {
            return true; // padrão: apenas ativos
        }
        String v = ativo.trim().toLowerCase();
        if ("todos".equals(v)) {
            return null; // null = sem filtro (todos)
        }
        if ("true".equals(v)) {
            return true;
        }
        if ("false".equals(v)) {
            return false;
        }
        throw new ValidationException("Parâmetro ativo deve ser true, false ou todos");
    }
}
