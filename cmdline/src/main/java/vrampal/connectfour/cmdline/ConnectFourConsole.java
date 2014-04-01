package vrampal.connectfour.cmdline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameImpl;

public class ConnectFourConsole {

  public static void main(String[] args) {
    new ConnectFourConsole().play();
  }

  private final Game game = new GameImpl("");

  private final BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));

  private final PrintStream out = System.out;

  public void play() {
    game.start();
    while (game.getStatus() != GameStatus.FINISHED) {
      try {
        int colIdx = readColIdx();
        game.dropDisc(colIdx - 1);
        printBoard(game.getBoard());
      } catch (ConnectFourException e) {
        out.println(e.getMessage());
      }
    }

    Player winner = game.getWinner();
    if (winner != null) {
      out.println(winner.getName() + " won the game.");
    } else {
      out.println("It's a draw game.");
    }
  }

  private int readColIdx() {
    int colIdx = -1;
    while (colIdx < 0) {
      try {
        out.println("Now playing: " + game.getCurrentPlayer().getName());
        out.print("Type a column number: ");
        String line = buffRead.readLine();
        if (line != null) {
          colIdx = Integer.parseInt(line);
        }
      } catch (IOException | NumberFormatException e) {
        out.println("Error while reading your input.");
      }
    }
    return colIdx;
  }

  private void printBoard(Board board) {
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

}
