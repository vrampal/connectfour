package vrampal.connectfour.cmdline;

import java.io.PrintStream;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameImpl;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractConnectFour implements Runnable {

  private final boolean enableDiplay;

  // TODO use dependency injection
  @Getter
  protected final Game game = new GameImpl();

  protected final PrintStream out = System.out;

  protected abstract int selectPlayColumn();

  @Override
  public void run() {
    if (enableDiplay) {
      printBeforeGame();
    }

    game.begin();
    while (game.getStatus() != GameStatus.FINISHED) {
      try {
        int colIdx = selectPlayColumn();
        game.dropDisc(colIdx);
        if (enableDiplay) {
          printBoard();
        }
      } catch (ConnectFourException e) {
        out.println(e.getMessage());
      }
    }

    if (enableDiplay) {
      printAfterGame();
    }
  }

  private void printBeforeGame() {
    out.println("Game id: " + game.getId());
  }

  private void printBoard() {
    Board board = game.getBoard();
    int height = board.getHeight();
    int width = board.getWidth();

    out.println();
    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
      out.print(rowIdx + " ");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        char c = board.getCell(colIdx - 1, rowIdx - 1).getLetter();
        out.print(c + " ");
      }
      out.println();
    }

    out.print("  ");
    for (int colIdx = 1; colIdx <= width; colIdx++) {
      out.print(colIdx + " ");
    }
    out.println();
    out.println();
  }

  private void printAfterGame() {
    Player winner = game.getWinner();
    if (winner != null) {
      out.println(winner.getName() + " won the game!");
    } else {
      out.println("It's a draw game.");
    }
  }

}
