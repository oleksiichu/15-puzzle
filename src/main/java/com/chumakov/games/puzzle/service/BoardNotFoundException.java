package com.chumakov.games.puzzle.service;

public class BoardNotFoundException extends ServiceException {
  private int id;

  BoardNotFoundException(int id) {
    this.id = id;
  }

  public String getMessage() {
    return String.format("Board was not found [board ID = %s]", id);
  }
}
