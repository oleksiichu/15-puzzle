package com.chumakov.games.puzzle.dao;

import com.chumakov.games.puzzle.entity.BoardEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface BoardRepository extends Repository<BoardEntity, Integer> {
  Optional<BoardEntity> findById(int id);

  BoardEntity save(BoardEntity entity);
}
