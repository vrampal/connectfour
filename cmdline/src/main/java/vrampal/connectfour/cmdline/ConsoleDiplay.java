package vrampal.connectfour.cmdline;

import java.io.PrintStream;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

public class ConsoleDiplay implements GameMonitor {

  private static final PrintStream OUT = System.out;

  @Override
  public void begin(Game game) {
    OUT.println("Game id: " + game.getId());
  }

  @Override
  public void played(Game game, Player prevPlayer, int prevColIdx) {
    OUT.println(prevPlayer.getName() + " played in column " + prevColIdx);
    printBoard(game.getBoard());
  }

  private void printBoard(Board board) {
    int height = board.getHeight();
    int width = board.getWidth();

    OUT.println();
    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
      OUT.print(rowIdx + " ");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        char c = board.getCell(colIdx - 1, rowIdx - 1).getLetter();
        OUT.print(c + " ");
      }
      OUT.println();
    }

    OUT.print("  ");
    for (int colIdx = 1; colIdx <= width; colIdx++) {
      OUT.print(colIdx + " ");
    }
    OUT.println();
    OUT.println();
  }

  @Override
  public void drawGame(Game game) {
    OUT.println("It's a draw game.");
  }

  @Override
  public void victory(Game game, Player winner) {
    OUT.println(winner.getName() + " won the game!");
  }

}
