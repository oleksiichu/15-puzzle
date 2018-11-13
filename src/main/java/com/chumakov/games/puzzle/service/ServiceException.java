package com.chumakov.games.puzzle.service;

public class ServiceException extends RuntimeException {
  ServiceException() {
  }

  ServiceException(Throwable cause) {
    super(cause);
  }
}
