package com.devpedrogo.tre_testes.exception;

import java.time.OffsetDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  // @ExceptionHandler(ParametroInvalidoException.class)
  // protected ResponseEntity<ResponseError> handleParametroInvalidoException(ParametroInvalidoException exception) {

  //   ResponseError respostaErro = new ResponseError(
  //     exception.getMessage(),
  //     HttpStatusCode.BAD_REQUEST.value(),
  //     OffsetDateTime.now());

  //   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
  // }

  // Trata o erro do @Pattern (validação de parâmetros na entrada do Controller)
  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ResponseError> handleConstraintViolationException(ConstraintViolationException exception) {
    String mensagemErro = exception.getConstraintViolations().iterator().next().getMessage();

    ResponseError respostaErro = new ResponseError(
      mensagemErro,
      HttpStatus.BAD_REQUEST.value(),
      OffsetDateTime.now()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
  }

}

