package com.chumakov.games.puzzle.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CorrectShiftRequestTest {
  private BoardModel model = new BoardModel();

  private List<Integer> givenState;
  private int col;
  private int row;
  private List<Integer> expectedState;

  public CorrectShiftRequestTest(List<Integer> givenState, int col, int row, List<Integer> expectedState) {
    this.givenState = givenState;
    this.col = col;
    this.row = row;
    this.expectedState = expectedState;
  }

  @Parameters(
      name = "{index}: {0} -> {1}/{2} = {3}"
  )
  public static Collection<Object[]> data() {
    return asList(
        new Object[] {
            asList(
                 1,  2,  3,  4,
                 5,  6,  7,  8,
                 9, 10, 11, 12,
                13, 14, 15,  0), 1, 4,
            asList(
                1,  2,  3,  4,
                5,  6,  7,  8,
                9, 10, 11, 12,
                0, 13, 14, 15)},
        new Object[] {
            asList(
                 1,  2,  3,  4,
                 5,  6,  0,  8,
                 9, 10, 11, 12,
                13, 14, 15,  7), 3, 1,
            asList(
                 1,  2,  0,  4,
                 5,  6,  3,  8,
                 9, 10, 11, 12,
                13, 14, 15,  7)},
        new Object[] {
            asList(
                1,  2,  3,  4,
                5,  6,  0,  8,
                9, 10, 11, 12,
                3, 14, 15,  7), 3, 4,
            asList(
                1,  2,  3,  4,
                5,  6, 11,  8,
                9, 10, 15, 12,
                3, 14,  0,  7)},
        new Object[] {
            asList(
                1,  2,  3,  4,
                5,  6,  0,  8,
                9, 10, 11, 12,
                3, 14, 15,  7), 1, 2,
            asList(
                1,  2,  3,  4,
                0,  5,  6,  8,
                9, 10, 11, 12,
                3, 14, 15,  7)},
        new Object[] {
            asList(
                1,  2,  3,  4,
                5,  6,  0,  8,
                9, 10, 11, 12,
                3, 14, 15,  7), 4, 2,
            asList(
                1,  2,  3,  4,
                5,  6,  8,  0,
                9, 10, 11, 12,
                3, 14, 15,  7)});
  }

  @Test
  public void boardStateAfterShiftShouldBeAsExpected() {
    assertEquals(model.shiftTiles(givenState, col, row), expectedState);
  }
}
