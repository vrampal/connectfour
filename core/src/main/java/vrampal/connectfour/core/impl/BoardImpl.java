package vrampal.connectfour.core.impl;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Player;

@Slf4j
@EqualsAndHashCode(of = { "content" })
class BoardImpl implements Board, Serializable {

  private static final long serialVersionUID = -5581793932297360395L;

  static final Player EMPTY_PLAYER = new PlayerImpl("Empty", ' ');

  private static final int DEFAULT_WIDTH = 7;

  private static final int DEFAULT_HEIGHT = 6;

  private static final int LENGTH_TO_WIN = 4;

  @Setter(AccessLevel.PACKAGE)
  private GameEndListener endGameListener;

  private final Player[][] content;

  /**
   * Create a board with default size (7x6).
   */
  BoardImpl() {
    this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  /**
   * Create a board with custom size.
   */
  BoardImpl(int width, int height) {
    if (width <= 0) {
      throw new ConnectFourException("Invalid width: " + width);
    }
    if (height <= 0) {
      throw new ConnectFourException("Invalid height: " + height);
    }

    content = new Player[width][];
    for (int colIdx = 0; colIdx < width; colIdx++) {
      content[colIdx] = new Player[height];
    }

    reset();

    if (log.isInfoEnabled()) {
      log.info("Creating new board " + width + 'x' + height);
    }
  }

  void reset() {
    for (Player[] column : content) {
      for (int rowIdx = 0; rowIdx < column.length; rowIdx++) {
        column[rowIdx] = EMPTY_PLAYER;
      }
    }
  }

  @Override
  public int getWidth() {
    return content.length;
  }

  @Override
  public int getHeight() {
    return content[0].length;
  }

  @Override
  public Player getCell(int colIdx, int rowIdx) {
    checkColIdx(colIdx);
    checkRowIdx(rowIdx);
    return getCellFast(colIdx, rowIdx);
  }

  @Override
  public Player getEmptyPlayer() {
    return EMPTY_PLAYER;
  }

  @Override
  public boolean isColumnFull(int colIdx) {
    checkColIdx(colIdx);
    Player[] column = content[colIdx];
    return isColumnFull(column);
  }

  @Override
  public boolean isFull() {
    for (Player[] column : content) {
      if (!isColumnFull(column)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Player entry point, drop a disc into a given column.
   */
  void dropDisc(Player player, int colIdx) {
    if (player == EMPTY_PLAYER) {
      throw new ConnectFourException("Empty player not allowed to play");
    }
    checkColIdx(colIdx);
    if (isColumnFull(colIdx)) {
      throw new ConnectFourException("Column is full");
    }

    Player[] column = content[colIdx];
    int rowIdx = column.length;
    while ((rowIdx > 0) && (column[rowIdx - 1] == EMPTY_PLAYER)) {
      rowIdx--;
    }
    column[rowIdx] = player;

    if (endGameListener != null) {
      checkForGameEnd(colIdx, rowIdx);
    }
  }

  private void checkColIdx(int colIdx) {
    if ((colIdx < 0) || (colIdx >= getWidth())) {
      throw new ConnectFourException("Invalid column id:" + colIdx);
    }
  }

  private void checkRowIdx(int rowIdx) {
    if ((rowIdx < 0) || (rowIdx >= getHeight())) {
      throw new ConnectFourException("Invalid row id:" + rowIdx);
    }
  }

  private boolean isColumnFull(Player[] column) {
    return column[column.length - 1] != EMPTY_PLAYER;
  }

  private Player getCellFast(int colIdx, int rowIdx) {
    return content[colIdx][rowIdx];
  }

  private void checkForGameEnd(int colIdx, int rowIdx) {
    Player player = content[colIdx][rowIdx];

    // Does the latest drop creates an horizontal line ?
    if (getHorizontalLength(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
      // Does the latest drop creates a vertical line ?
    } else if (getVerticalLength(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
      // Does the latest drop creates a diagonal line ?
    } else if (getDiagonalLength1(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
      // Does the latest drop creates a diagonal line ?
    } else if (getDiagonalLength2(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
    } else if (isFull()) {
      endGameListener.drawGame();
    }
  }

  /**
   * Returns the length of the horizontal line from a given point.
   */
  private int getHorizontalLength(int colIdx, int rowIdx) {
    Player player = content[colIdx][rowIdx];

    int columnLeft = colIdx;
    while ((columnLeft > 0) && (getCellFast(columnLeft - 1, rowIdx) == player)) {
      columnLeft--;
    }
    int columnRight = colIdx;
    while ((columnRight < (getWidth() - 1)) && (getCellFast(columnRight + 1, rowIdx) == player)) {
      columnRight++;
    }

    return columnRight - columnLeft + 1;
  }

  /**
   * Returns the length of the vertical line from a given point.
   */
  private int getVerticalLength(int column, int row) {
    Player player = content[column][row];

    int rowDown = row;
    while ((rowDown > 0) && (getCellFast(column, rowDown - 1) == player)) {
      rowDown--;
    }
    int rowUp = row;
    while ((rowUp < (getHeight() - 1)) && (getCellFast(column, rowUp + 1) == player)) {
      rowUp++;
    }

    return rowUp - rowDown + 1;
  }

  /**
   * Returns the length of a diagonal line from a given point.
   */
  private int getDiagonalLength1(int column, int row) {
    Player player = content[column][row];

    int columnLeft = column;
    int rowDown = row;
    while ((columnLeft > 0) && (rowDown > 0) && (getCellFast(columnLeft - 1, rowDown - 1) == player)) {
      columnLeft--;
      rowDown--;
    }
    int columnRight = column;
    int rowUp = row;
    while ((columnRight < (getWidth() - 1)) && (rowUp < (getHeight() - 1))
        && (getCellFast(columnRight + 1, rowUp + 1) == player)) {
      columnRight++;
      rowUp++;
    }

    return columnRight - columnLeft + 1;
  }

  /**
   * Returns the length of another diagonal line from a given point.
   */
  private int getDiagonalLength2(int column, int row) {
    Player player = content[column][row];

    int columnLeft = column;
    int rowUp = row;
    while ((columnLeft > 0) && (rowUp < (getHeight() - 1)) && (getCellFast(columnLeft - 1, rowUp + 1) == player)) {
      columnLeft--;
      rowUp++;
    }
    int columnRight = column;
    int rowDown = row;
    while ((columnRight < (getWidth() - 1)) && (rowDown > 0) && (getCellFast(columnRight + 1, rowDown - 1) == player)) {
      columnRight++;
      rowDown--;
    }

    return columnRight - columnLeft + 1;
  }

}
