package vrampal.connectfour.cmdline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

  private final Game game = new GameImpl();

  private final BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));

  public void play() {
    while (game.getStatus() != GameStatus.FINISHED) {
      try {
        int colIdx = readColIdx();
        game.dropDisc(colIdx - 1);
        printBoard(game.getBoard());
      } catch (ConnectFourException e) {
        System.out.println(e.getMessage());
      }
    }

    Player winner = game.getWinner();
    if (winner != null) {
      System.out.println(winner.getName() + " win the game.");
    } else {
      System.out.println("It's a draw game.");
    }
  }

  private int readColIdx() {
    int colIdx = -1;
    while (colIdx < 0) {
      try {
        System.out.println("Now playing: " + game.getCurrentPlayer().getName());
        System.out.print("Type a column number: ");
        String line = buffRead.readLine();
        if (line != null) {
          colIdx = Integer.parseInt(line);
        }
      } catch (IOException | NumberFormatException e) {
        System.out.println("Error while reading your input.");
      }
    }
    return colIdx;
  }

  private void printBoard(Board board) {
    int width = board.getWidth();
    int height = board.getHeight();

    System.out.println();
    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
      System.out.print(rowIdx + " ");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        char c = board.getCell(colIdx - 1, rowIdx - 1).getLetter();
        System.out.print(c + " ");
      }
      System.out.println();
    }

    System.out.print("  ");
    for (int colIdx = 1; colIdx <= width; colIdx++) {
      System.out.print(colIdx + " ");
    }
    System.out.println();
    System.out.println();
  }

}
