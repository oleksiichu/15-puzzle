package com.chumakov.games.puzzle.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class WrongShiftRequestTest {
  private BoardModel model = new BoardModel();

  private List<Integer> givenState;
  private int col;
  private int row;

  public WrongShiftRequestTest(List<Integer> givenState, int col, int row) {
    this.givenState = givenState;
    this.col = col;
    this.row = row;
  }

  @Parameters(
      name = "{index}: {0} -> {1}/{2} = exception"
  )
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[] {
            asList(
                 1,  2,  3,  4,
                 5,  6,  7,  8,
                 9, 10, 11, 12,
                13, 14, 15,  0), 4, 4},
        new Object[] {
            asList(
                 1,  2,  3,  4,
                 5,  6,  0,  8,
                 9, 10, 11, 12,
                13, 14, 15,  7), 2, 3},
        new Object[] {
            asList(
                1,  2,  3,  4,
                5,  6,  0,  8,
                9, 10, 11, 12,
                3, 14, 15,  7), 4, 1},
        new Object[] {
            asList(
                1,  2,  3,  4,
                5,  6,  0,  8,
                9, 10, 11, 12,
                3, 14, 15,  7), 2, 4},
        new Object[] {
            asList(
                1,  2,  3,  4,
                5,  6,  0,  8,
                9, 10, 11, 12,
                3, 14, 15,  7), 4, 3});
  }

  @Test(
      expected = WrongShiftRequestException.class
  )
  public void impossibleShiftRequestShouldThrowException() {
    model.shiftTiles(givenState, col, row);
  }
}
