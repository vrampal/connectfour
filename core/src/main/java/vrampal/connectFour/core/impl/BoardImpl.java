package vrampal.connectFour.core.impl;

import vrampal.connectFour.core.Board;
import vrampal.connectFour.core.ConnectFourException;
import vrampal.connectFour.core.Player;

public class BoardImpl implements Board {

  private static final int DEFAULT_WIDTH = 7;

  private static final int DEFAULT_HEIGHT = 6;

  private static final int LENGTH_TO_WIN = 4;

  private final GameEndListener endGameListener;

  private final Player[][] content;

  /**
   * Create a board with default size (7x6).
   */
  BoardImpl(GameEndListener endGameListener) {
    this(endGameListener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  /**
   * Create a board with custom size.
   */
  BoardImpl(GameEndListener endGameListener, int width, int height) {
    if (width <= 0) {
      throw new ConnectFourException("Invalid width: " + width);
    }
    if (height < 0) {
      throw new ConnectFourException("Invalid height: " + height);
    }

    this.endGameListener = endGameListener;

    content = new Player[width][];
    for (int colIdx = 0; colIdx < width; colIdx++) {
      content[colIdx] = new Player[height];
    }

    reset();
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

  void reset() {
    for (Player[] column : content) {
      for (int rowIdx = 0; rowIdx < column.length; rowIdx++) {
        column[rowIdx] = PlayerImpl.EMPTY;
      }
    }
  }

  /**
   * Player entry point, drop a disc into a given column.
   */
  void dropDisc(Player player, int colIdx) {
    checkColIdx(colIdx);

    Player[] column = content[colIdx];
    if (column[column.length - 1] != PlayerImpl.EMPTY) {
      throw new ConnectFourException("Column is full");
    }

    int rowIdx = column.length;
    while ((rowIdx > 0) && (column[rowIdx - 1] == PlayerImpl.EMPTY)) {
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

  private Player getCellFast(int colIdx, int rowIdx) {
    return content[colIdx][rowIdx];
  }

  private boolean isFull() {
    for (Player[] column : content) {
      if (column[column.length - 1] == PlayerImpl.EMPTY) {
        return false;
      }
    }
    return true;
  }

  private void checkForGameEnd(int colIdx, int rowIdx) {
    Player player = content[colIdx][rowIdx];

    // Does the latest drop creates an horizontal line ?
    if (getHorizontalLength(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
    }
    // Does the latest drop creates a vertical line ?
    else if (getVerticalLength(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
    }
    // Does the latest drop creates a diagonal line ?
    else if (getDiagonalLength1(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
    }
    // Does the latest drop creates a diagonal line ?
    else if (getDiagonalLength2(colIdx, rowIdx) >= LENGTH_TO_WIN) {
      endGameListener.victory(player);
    }
    else if (isFull()) {
      endGameListener.draw();
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

    int length = columnRight - columnLeft + 1;
    return length;
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

    int length = rowUp - rowDown + 1;
    return length;
  }

  /**
   * Returns the length of a diagonal line from a given point.
   */
  private int getDiagonalLength1(int column, int row) {
    Player player = content[column][row];

    int columnLeft = column;
    int rowDown = row;
    while ((columnLeft > 0) && (rowDown > 0) && (getCell(columnLeft - 1, rowDown - 1) == player)) {
      columnLeft--;
      rowDown--;
    }
    int columnRight = column;
    int rowUp = row;
    while ((columnRight < (getWidth() - 1)) && (rowUp < (getHeight() - 1))
        && (getCell(columnRight + 1, rowUp + 1) == player)) {
      columnRight++;
      rowUp++;
    }

    int length = columnRight - columnLeft + 1;
    return length;
  }

  /**
   * Returns the length of another diagonal line from a given point.
   */
  private int getDiagonalLength2(int column, int row) {
    Player player = content[column][row];

    int columnLeft = column;
    int rowUp = row;
    while ((columnLeft > 0) && (rowUp < (getHeight() - 1)) && (getCell(columnLeft - 1, rowUp + 1) == player)) {
      columnLeft--;
      rowUp++;
    }
    int columnRight = column;
    int rowDown = row;
    while ((columnRight < (getWidth() - 1)) && (rowDown > 0) && (getCell(columnRight + 1, rowDown - 1) == player)) {
      columnRight++;
      rowDown--;
    }

    int length = columnRight - columnLeft + 1;
    return length;
  }

}
