package com.chumakov.games.puzzle.service;

import com.chumakov.games.puzzle.dao.BoardRepository;
import com.chumakov.games.puzzle.entity.BoardEntity;
import com.chumakov.games.puzzle.model.BoardModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class BoardService {
  private final Logger logger = LoggerFactory.getLogger(BoardService.class);
  private final BoardRepository boardRepository;
  private final BoardModel boardModel;

  @Autowired
  public BoardService(BoardRepository boardRepository, BoardModel boardModel) {
    this.boardRepository = boardRepository;
    this.boardModel = boardModel;
  }

  public BoardEntity getBoard(int id) {
    return withExceptionHandling(() -> boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException(id)));
  }

  @Transactional
  public BoardEntity updateBoard(int id, int col, int row) {
    return withExceptionHandling(() ->
        boardRepository.findById(id)
            .map(board -> tryShiftTiles(board, col, row))
            .map(boardRepository::save)
            .orElseThrow(() -> new BoardNotFoundException(id)));
  }

  public BoardEntity createBoard() {
    return withExceptionHandling(() -> boardRepository.save(new BoardEntity(boardModel.createTiles())));
  }

  private BoardEntity tryShiftTiles(BoardEntity board, int col, int row) {
    board.setTiles(boardModel.shiftTiles(board.getTiles(), col, row));
    return board;
  }

  private BoardEntity withExceptionHandling(Supplier<BoardEntity> supplier) {
    try {
      return supplier.get();
    }
    catch (Exception e) {
      logger.error(e.getMessage());
      if (e instanceof ServiceException) {
        throw e;
      }
      else {
        throw new ServiceException(e);
      }
    }
  }
}
