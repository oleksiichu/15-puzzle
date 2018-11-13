package com.chumakov.games.puzzle.controller;

import com.chumakov.games.puzzle.dao.BoardRepository;
import com.chumakov.games.puzzle.entity.BoardEntity;
import com.chumakov.games.puzzle.model.BoardModel;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest({BoardController.class})
public class BoardControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private BoardController boardController;

  @MockBean
  private BoardRepository boardRepository;

  @MockBean
  private BoardModel boardModel;

  private BoardEntity board = new BoardEntity(Arrays.asList(1, 2, 0));

  @Test
  public void getBoardTest() throws Exception {
    given(this.boardController.getBoard(1)).willReturn(board);

    mvc.perform(MockMvcRequestBuilders.get("/boards/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("id", is(board.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("tiles", is(board.getTiles())));
  }

  @Test
  public void updateBoardTest() throws Exception {
    given(this.boardController.updateBoard(1, 1, 1)).willReturn(board);

    mvc.perform(MockMvcRequestBuilders.post("/boards/1/1/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("id", is(board.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("tiles", is(board.getTiles())));
  }

  @Test
  public void createBoardTest() throws Exception {
    given(this.boardController.createBoard()).willReturn(board);

    mvc.perform(MockMvcRequestBuilders.put("/boards").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("id", is(board.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("tiles", Is.is(board.getTiles())));
  }
}
