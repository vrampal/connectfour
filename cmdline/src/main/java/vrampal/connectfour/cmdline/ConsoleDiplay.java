package vrampal.connectfour.cmdline;

import java.io.PrintStream;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

// TODO move to logger ?
public class ConsoleDiplay implements GameMonitor {

  private static final PrintStream OUT = System.out;

  @Override
  public void onBegin(Game game) {
    println("Game id: " + game.getId());
  }

  @Override
  public void onPlay(Game game, Player player, int colIdx) {
    println(player.getName() + " played in column " + (colIdx + 1));
    printBoard(game.getBoard());
  }

  private void printBoard(Board board) {
    int height = board.getHeight();
    int width = board.getWidth();

    println();

    // Print content
    StringBuilder buff;
    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
      buff = new StringBuilder();
      buff.append(rowIdx);
      buff.append(' ');
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        char c = board.getCell(colIdx - 1, rowIdx - 1).getLetter();
        buff.append(c);
        buff.append(' ');
      }
      println(buff.toString());
    }

    // print footer
    buff = new StringBuilder();
    buff.append("  ");
    for (int colIdx = 1; colIdx <= width; colIdx++) {
      buff.append(colIdx);
      buff.append(' ');
    }
    println(buff.toString());

    println();
  }

  @Override
  public void onDraw(Game game) {
    println("It's a draw game.");
  }

  @Override
  public void onVictory(Game game, Player winner) {
    println(winner.getName() + " won the game!");
  }

  @Override
  public void onError(Game game, ConnectFourException expection) {
    println("Error: " + expection.getMessage());
  }

  private void println() {
    OUT.println();
  }

  private void println(String message) {
    OUT.println(message);
  }

}
