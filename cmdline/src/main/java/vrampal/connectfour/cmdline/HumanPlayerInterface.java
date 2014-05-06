package vrampal.connectfour.cmdline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;

//TODO move to logger ?
public class HumanPlayerInterface implements PlayerInterface {

  private static final PrintStream OUT = System.out;

  private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));

  @Override
  public int selectPlayColumn(Game game) {
    Board board = game.getBoard();
    int width = board.getWidth();
    int colIdx = 0;
    while ((colIdx < 1) || (colIdx > width)) {
      try {
        println("Now playing: " + game.getCurrentPlayer().getName());
        print("Type a column number [1.." + width + "]: ");
        String line = IN.readLine();
        if (line != null) {
          colIdx = Integer.parseInt(line);
        }
      } catch (IOException | NumberFormatException e) {
        println("Error while reading your input.");
      }
    }
    return colIdx - 1;
  }

  private void print(String message) {
    OUT.print(message);
  }

  private void println(String message) {
    OUT.println(message);
  }

}
