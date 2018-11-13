package com.chumakov.games.puzzle.controller;

import com.chumakov.games.puzzle.entity.BoardEntity;
import com.chumakov.games.puzzle.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/boards"})
public class BoardController {
  private final BoardService boardService;

  @Autowired
  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping(
      value = {"/{id}"},
      produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public BoardEntity getBoard(@PathVariable("id") int id) {
    return boardService.getBoard(id);
  }

  @PutMapping(
      produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public BoardEntity createBoard() {
    return boardService.createBoard();
  }

  @PostMapping(
      value = {"/{id}/{col}/{row}"},
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  public BoardEntity updateBoard(
      @PathVariable("id") int id,
      @PathVariable("col") int col,
      @PathVariable("row") int row) {
    return boardService.updateBoard(id, col, row);
  }
}
