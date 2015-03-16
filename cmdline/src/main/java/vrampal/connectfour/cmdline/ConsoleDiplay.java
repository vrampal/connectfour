package vrampal.connectfour.cmdline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.GameReadOnly;
import vrampal.connectfour.core.Player;

public class ConsoleDiplay implements GameMonitor {

  private static final Logger CONSOLE = LoggerFactory.getLogger("connectfour.console");

  @Override
  public void onBegin(GameReadOnly game) {
    println("Game id: " + game.getId());
  }

  @Override
  public void onPlay(GameReadOnly game, Player player, int colIdx, int rowIdx) {
    println(player.getName() + " played in column " + (colIdx + 1) + ", row " + (rowIdx + 1));
    printBoard(game.getBoard());
  }

  private void printBoard(Board board) {
    int height = board.getHeight();
    int width = board.getWidth();

    println("");

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

    println("");
  }

  @Override
  public void onDraw(GameReadOnly game) {
    println("It's a draw game.");
  }

  @Override
  public void onVictory(GameReadOnly game, Player winner) {
    println(winner.getName() + " won the game!");
  }

  @Override
  public void onError(GameReadOnly game, ConnectFourException expection) {
    println("Error: " + expection.getMessage());
  }

  private void println(String message) {
    CONSOLE.info(message);
  }

}
