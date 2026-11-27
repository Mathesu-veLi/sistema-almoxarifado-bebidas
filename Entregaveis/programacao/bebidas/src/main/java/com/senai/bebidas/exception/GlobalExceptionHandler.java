package com.senai.bebidas.exception;

import com.senai.bebidas.dto.MensagemErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Tratador padronizado de erros.
 * Todas as respostas de erro seguem o formato exigido pelo contrato:
 * { status, mensagem, timestamp }.
 * Nao expoe stack trace nem detalhes internos.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Erros de validacao de entrada (ex: quantidade invalida, data futura).
     * Responde 400 com mensagem descritiva.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MensagemErroDTO> handleValidacaoEntrada(ValidationException ex) {
        MensagemErroDTO erro = new MensagemErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                OffsetDateTime.now().toString());
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    /**
     * Erros de validacao de campos (@NotBlank, @Size, @Positive, etc).
     * Responde 400 com mensagem descritiva.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroDTO> handleValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nomeCampo = ((FieldError) error).getField();
            String mensagemErro = error.getDefaultMessage();
            erros.put(nomeCampo, mensagemErro);
        });
        String mensagem = "Erro de validacao: " + erros;
        MensagemErroDTO erro = new MensagemErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                mensagem,
                OffsetDateTime.now().toString());
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    /**
     * Recurso nao encontrado (ID invalido).
     * Responde 404.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MensagemErroDTO> handleNaoEncontrado(EntityNotFoundException ex) {
        MensagemErroDTO erro = new MensagemErroDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                OffsetDateTime.now().toString());
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    /**
     * Regra de negocio violada (estoque insuficiente, login duplicado, etc).
     * Responde 409.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MensagemErroDTO> handleNegocio(BusinessException ex) {
        MensagemErroDTO erro = new MensagemErroDTO(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                OffsetDateTime.now().toString());
        return new ResponseEntity<>(erro, HttpStatus.CONFLICT);
    }

    /**
     * Token nao fornecido ou qualquer excecao inesperada nao tratada acima.
     * Responde 401 quando for falta de token, 500 para o resto.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MensagemErroDTO> handleRuntime(RuntimeException ex) {
        String msg = ex.getMessage();
        if (msg != null && msg.contains("Token nao fornecido")) {
            MensagemErroDTO erro = new MensagemErroDTO(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Autenticacao necessaria",
                    OffsetDateTime.now().toString());
            return new ResponseEntity<>(erro, HttpStatus.UNAUTHORIZED);
        }
        MensagemErroDTO erro = new MensagemErroDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                OffsetDateTime.now().toString());
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Qualquer outra excecao nao tratada.
     * Responde 500 sem expor detalhes internos.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemErroDTO> handleGenerico(Exception ex) {
        MensagemErroDTO erro = new MensagemErroDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                OffsetDateTime.now().toString());
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
