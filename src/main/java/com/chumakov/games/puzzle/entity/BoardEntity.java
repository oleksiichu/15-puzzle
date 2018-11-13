package com.chumakov.games.puzzle.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class BoardEntity {
  @Id
  @GeneratedValue
  private int id;
  @ElementCollection(fetch = FetchType.EAGER)
  private List<Integer> tiles;

  public BoardEntity() {
  }

  public BoardEntity(List<Integer> tiles) {
    this.tiles = new ArrayList<>(tiles);
  }

  public int getId() {
    return id;
  }

  public List<Integer> getTiles() {
    return tiles;
  }

  public void setTiles(List<Integer> tiles) {
    this.tiles = tiles;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    else if (!(other instanceof BoardEntity)) {
      return false;
    }
    else {
      BoardEntity otherBoard = (BoardEntity) other;
      return id == otherBoard.id && tiles.equals(otherBoard.tiles);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tiles);
  }
}
