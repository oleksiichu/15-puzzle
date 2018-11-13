package com.chumakov.games.puzzle.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardModel {
  private static final int SHUFFLE_FACTOR = 3;
  private static final int BOARD_SIZE = 4;
  private static final int TILES_COUNT = BOARD_SIZE * BOARD_SIZE;
  private static final int EMPTY_TILE_VALUE = 0;
  private static final int NOT_FOUND_VALUE = -1;

  private final Logger logger = LoggerFactory.getLogger(BoardModel.class);

  private final BiFunction<List<Integer>, IntUnaryOperator, List<Integer>> boardUpdater = (board, shifter) ->
      IntStream.range(0, board.size()).map(shifter).boxed().collect(Collectors.toList());

  public List<Integer> createTiles() {
    Random random = new Random();
    BitSet shuffledPositions = new BitSet(TILES_COUNT - 1);

    IntSupplier randomPos = () -> {
      if (shuffledPositions.cardinality() == TILES_COUNT - 1) {
        String message = "All tiles were already involved in a shuffle, there is nothing to change";
        logger.error(message);
        throw new ModelException(message);
      }

      int pos;

      do {
        pos = random.nextInt(TILES_COUNT - 1);
      }
      while (shuffledPositions.get(pos));

      shuffledPositions.set(pos);
      return pos;
    };

    List<Integer> tiles = IntStream.range(1, TILES_COUNT).boxed().collect(Collectors.toList());

    for (int i = 0; i < SHUFFLE_FACTOR * 2; ++i) {
      Collections.swap(tiles, randomPos.getAsInt(), randomPos.getAsInt());
    }

    tiles.add(EMPTY_TILE_VALUE);
    return tiles;
  }

  public List<Integer> shiftTiles(List<Integer> tiles, int col, int row) {
    int startRowPos = (row - 1) * BOARD_SIZE;

    if (col < 1 || col > BOARD_SIZE
        || row < 1 || row > BOARD_SIZE
        || tiles.get(startRowPos + col - 1) == EMPTY_TILE_VALUE) {

      logger.error(String.format("Wrong shift request for position: [col = %s, row = %s]", col, row));
      throw new WrongShiftRequestException(col, row);
    }

    Optional<IntUnaryOperator> rowShifter = tryShift(getLine(tiles, startRowPos, i -> i + 1), col - 1)
        .map(shiftedLine -> getBaseShifter(shiftedLine, tiles))
        .map(baseShifter -> baseShifter.apply(p -> p >= startRowPos && p < startRowPos + BOARD_SIZE,
            p -> p - startRowPos));

    if (rowShifter.isPresent()) {
      return boardUpdater.apply(tiles, rowShifter.get());
    }

    Optional<IntUnaryOperator> colShifter = tryShift(getLine(tiles, col - 1, i -> i + BOARD_SIZE), row - 1)
        .map(shiftedLine -> getBaseShifter(shiftedLine, tiles))
        .map(baseShifter -> baseShifter.apply(p -> p % BOARD_SIZE == col - 1, p -> p / BOARD_SIZE));

    if (colShifter.isPresent()) {
      return boardUpdater.apply(tiles, colShifter.get());
    }

    logger.error(String.format("Wrong shift request for position: [col = %s, row = %s]", col, row));
    throw new WrongShiftRequestException(col, row);
  }

  private BiFunction<IntPredicate, IntFunction<Integer>, IntUnaryOperator> getBaseShifter(List<Integer> shiftedLine,
      List<Integer> tiles) {
    return (t, s) -> p -> t.test(p) ? shiftedLine.get(s.apply(p)) : tiles.get(p);
  }

  private Optional<List<Integer>> tryShift(List<Integer> line, int pos) {
    int emptyTilePos = line.indexOf(EMPTY_TILE_VALUE);

    if (emptyTilePos != NOT_FOUND_VALUE) {

      if (emptyTilePos > pos) {
        line.remove(emptyTilePos);
        line.add(pos, EMPTY_TILE_VALUE);
      }
      else {
        line.add(pos + 1, EMPTY_TILE_VALUE);
        line.remove(emptyTilePos);
      }

      return Optional.of(line);
    }
    else {
      return Optional.empty();
    }
  }

  private List<Integer> getLine(List<Integer> tiles, int pos, IntUnaryOperator step) {
    return IntStream.iterate(pos, step).limit(BOARD_SIZE).mapToObj(tiles::get).collect(Collectors.toList());
  }
}
