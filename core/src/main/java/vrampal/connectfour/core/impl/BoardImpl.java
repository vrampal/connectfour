package vrampal.connectfour.core.impl;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.data.PlayerData;

/**
 * Empty cell use null internally but it use EMPTY_PLAYER externally.
 */
@Slf4j
@EqualsAndHashCode(of = { "content" })
class BoardImpl implements Board, Serializable {

  private static final long serialVersionUID = 9146512987657901915L;

  static final Player EMPTY_PLAYER = new PlayerData("Empty", ' ');

  private static final int DEFAULT_WIDTH = 7;

  private static final int DEFAULT_HEIGHT = 6;

  private static final int LENGTH_TO_WIN = 4;

  @Getter
  private final int width;

  @Getter
  private final int height;

  private final Player[] content;

  @Setter(AccessLevel.PACKAGE)
  private GameEndListener endGameListener;

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

    this.width = width;
    this.height = height;
    content = new Player[width * height];

    if (log.isInfoEnabled()) {
      log.info("Creating new board " + width + 'x' + height);
    }
  }

  // ----- Base methods for content manipulation -----

  private Player getCellFast(int colIdx, int rowIdx) {
    return content[(colIdx * height) + rowIdx];
  }

  private void setCellFast(int colIdx, int rowIdx, Player player) {
    content[(colIdx * height) + rowIdx] = player;
  }

  private boolean isColumnFullFast(int colIdx) {
    return content[((colIdx + 1) * height) - 1] != null;
  }

  // ----- All the following methods rely on getWidth(), getHeight(), getCellFast() and setCellFast() -----

  // ----- Input validation methods -----

  private void checkColIdx(int colIdx) {
    if ((colIdx < 0) || (colIdx >= getWidth())) {
      String message = "Invalid column id: " + colIdx;
      log.error(message);
      throw new ConnectFourException(message);
    }
  }

  private void checkRowIdx(int rowIdx) {
    if ((rowIdx < 0) || (rowIdx >= getHeight())) {
      String message = "Invalid row id: " + rowIdx;
      log.error(message);
      throw new ConnectFourException(message);
    }
  }

  // ----- The following methods rely on checkColIdx() and checkRowIdx() -----

  @Override
  public Player getCell(int colIdx, int rowIdx) {
    checkColIdx(colIdx);
    checkRowIdx(rowIdx);
    Player player = getCellFast(colIdx, rowIdx);
    if (player == null) {
      player = EMPTY_PLAYER;
    }
    return player;
  }

  @Override
  public boolean isColumnFull(int colIdx) {
    checkColIdx(colIdx);
    return isColumnFullFast(colIdx);
  }

  @Override
  public boolean isFull() {
    for (int colIdx = 0; colIdx < width; colIdx++) {
      if (!isColumnFullFast(colIdx)) {
        return false;
      }
    }
    return true;
  }

  // ----- Dropping and victory mechanics -----

  @Override
  public Player getEmptyPlayer() {
    return EMPTY_PLAYER;
  }

  /**
   * Play entry point, drop a disc into a given column, checks for victory or draw.
   *
   * @param player Player doing the drop.
   * @param colIdx Column of the drop.
   * @return Row of the drop.
   */
  int dropDisc(Player player, int colIdx) {
    if (player == null) {
      String message = "Null is not a valid player";
      log.error(message);
      throw new ConnectFourException(message);
    }
    if (player == EMPTY_PLAYER) {
      String message = "Empty player not allowed to play";
      log.error(message);
      throw new ConnectFourException(message);
    }
    // isColumnFull() also perform checkColIdx() no need to to it twice.
    if (isColumnFull(colIdx)) {
      String message = "Column is full";
      log.error(message);
      throw new ConnectFourException(message);
    }

    int rowIdx = getHeight() - 1;
    while ((rowIdx > 0) && (getCellFast(colIdx, rowIdx - 1) == null)) {
      rowIdx--;
    }
    setCellFast(colIdx, rowIdx, player);

    if (endGameListener != null) {
      checkForGameEnd(colIdx, rowIdx);
    }

    return rowIdx;
  }

  private void checkForGameEnd(int colIdx, int rowIdx) {
    Player player = getCellFast(colIdx, rowIdx);

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
    Player player = getCellFast(colIdx, rowIdx);

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
  private int getVerticalLength(int colIdx, int rowIdx) {
    Player player = getCellFast(colIdx, rowIdx);

    int rowDown = rowIdx;
    while ((rowDown > 0) && (getCellFast(colIdx, rowDown - 1) == player)) {
      rowDown--;
    }
    // Note: row up checking is impossible as the disc is always the top.

    return rowIdx - rowDown + 1;
  }

  /**
   * Returns the length of a diagonal line from a given point.
   */
  private int getDiagonalLength1(int colIdx, int rowIdx) {
    Player player = getCellFast(colIdx, rowIdx);

    int columnLeft = colIdx;
    int rowDown = rowIdx;
    while ((columnLeft > 0) && (rowDown > 0) && (getCellFast(columnLeft - 1, rowDown - 1) == player)) {
      columnLeft--;
      rowDown--;
    }
    int columnRight = colIdx;
    int rowUp = rowIdx;
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
  private int getDiagonalLength2(int colIdx, int rowIdx) {
    Player player = getCellFast(colIdx, rowIdx);

    int columnLeft = colIdx;
    int rowUp = rowIdx;
    while ((columnLeft > 0) && (rowUp < (getHeight() - 1)) && (getCellFast(columnLeft - 1, rowUp + 1) == player)) {
      columnLeft--;
      rowUp++;
    }
    int columnRight = colIdx;
    int rowDown = rowIdx;
    while ((columnRight < (getWidth() - 1)) && (rowDown > 0) && (getCellFast(columnRight + 1, rowDown - 1) == player)) {
      columnRight++;
      rowDown--;
    }

    return columnRight - columnLeft + 1;
  }

}
