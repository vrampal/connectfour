package vrampal.demo.connectFour.core;

class Board {

  private static final int DEFAULT_WIDTH = 7;
  private static final int DEFAULT_HEIGHT = 6;

  private final VictoryListener vicListener;
  private final Player[][] content;

  Board(VictoryListener vicListener) {
    this(vicListener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  Board(VictoryListener vicListener, int width, int height) {
    this.vicListener = vicListener;

    content = new Player[width][];
    for (int colIdx = 0; colIdx < width; colIdx++) {
      content[colIdx] = new Player[height];
    }

    reset();
  }

  void reset() {
    for (Player[] col : content) {
      for (int rowIdx = 0; rowIdx < col.length; rowIdx++) {
        col[rowIdx] = Player.EMPTY;
      }
    }
  }

  Player getContent(int rowIdx, int colIdx) {
    checkColIdx(colIdx);
    checkRowIdx(rowIdx);
    return content[colIdx][rowIdx];
  }

  void drop(Player player, int colIdx) {
    checkColIdx(colIdx);
    Player[] col = content[colIdx];
    if (col[col.length - 1] != Player.EMPTY) {
      throw new ConnectFourException("Collumn is full");
    }
    int rowIdx = col.length - 1;
    while ((rowIdx > 0) && (col[rowIdx] == Player.EMPTY)) {
      rowIdx--;
    }
    col[rowIdx + 1] = player;
    checkForVictory();
  }

  private void checkColIdx(int colIdx) {
    if ((colIdx < 0) || (colIdx >= content.length)) {
      throw new ConnectFourException("Invalid column id:" + colIdx);
    }
  }

  private void checkRowIdx(int rowIdx) {
    if ((rowIdx < 0) || (rowIdx >= content[0].length)) {
      throw new ConnectFourException("Invalid row id:" + rowIdx);
    }
  }

  private void checkForVictory() {
    // TODO
  }

}
