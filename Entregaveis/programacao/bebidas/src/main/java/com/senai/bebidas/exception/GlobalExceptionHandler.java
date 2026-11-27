package com.senai.bebidas.exception;

import com.senai.bebidas.dto.MensagemErroDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Contrato: "O campo nome e obrigatorio". Devolve a primeira mensagem legivel.
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .filter(m -> m != null && !m.isBlank())
                .distinct()
                .collect(Collectors.joining("; "));
        if (mensagem.isBlank()) {
            mensagem = "Erro de validação de entrada";
        }
        return erro(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MensagemErroDTO> handleValidacao(ValidationException ex) {
        return erro(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MensagemErroDTO> handleCorpoInvalido(HttpMessageNotReadableException ex) {
        return erro(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido ou campo com formato incorreto");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MensagemErroDTO> handleTipoParametro(MethodArgumentTypeMismatchException ex) {
        return erro(HttpStatus.BAD_REQUEST, "Parâmetro inválido: " + ex.getName());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<MensagemErroDTO> handleParametroAusente(MissingServletRequestParameterException ex) {
        return erro(HttpStatus.BAD_REQUEST, "O parâmetro " + ex.getParameterName() + " é obrigatório");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MensagemErroDTO> handleEntidadeNaoEncontrada(EntityNotFoundException ex) {
        return erro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MensagemErroDTO> handleNegocio(BusinessException ex) {
        return erro(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensagemErroDTO> handleIntegridade(DataIntegrityViolationException ex) {
        return erro(HttpStatus.CONFLICT, "Operação viola uma restrição de integridade dos dados");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemErroDTO> handleGeneric(Exception ex) {
        return erro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
    }

    private ResponseEntity<MensagemErroDTO> erro(HttpStatus status, String mensagem) {
        MensagemErroDTO erro = new MensagemErroDTO(status.value(), mensagem, OffsetDateTime.now().toString());
        return new ResponseEntity<>(erro, status);
    }
}
