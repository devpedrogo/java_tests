package com.devpedrogo.tre_testes.exception;

import java.time.OffsetDateTime;

/**
 * ResponseError
 */
public record ResponseError(
  String descricao,
  Integer status,
  OffsetDateTime horario) {

}

