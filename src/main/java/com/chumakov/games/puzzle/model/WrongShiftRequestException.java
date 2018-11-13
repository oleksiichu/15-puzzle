package com.chumakov.games.puzzle.model;

public class WrongShiftRequestException extends ModelException {
  private int col;
  private int row;

  WrongShiftRequestException(int col, int row) {
    this.col = col;
    this.row = row;
  }

  public String toString() {
    return String.format("Couldn't shift tiles from position [col = %s, row = %s]", col, row);
  }
}
