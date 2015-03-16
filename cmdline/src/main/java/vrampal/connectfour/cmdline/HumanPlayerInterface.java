package vrampal.connectfour.cmdline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;

public class HumanPlayerInterface implements PlayerInterface {

  private static final Logger CONSOLE = LoggerFactory.getLogger("connectfour.console");

  private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

  @Override
  public int selectPlayColumn(Game game) {
    Board board = game.getBoard();
    int width = board.getWidth();
    int colIdx = 0;
    while ((colIdx < 1) || (colIdx > width)) {
      try {
        println("Now playing: " + game.getCurrentPlayer().getName());
        println("Type a column number [1.." + width + "]: ");
        String line = INPUT.readLine();
        if (line != null) {
          colIdx = Integer.parseInt(line);
        }
      } catch (IOException | NumberFormatException e) {
        println("Error while reading your input.");
      }
    }
    return colIdx - 1;
  }

  private void println(String message) {
    CONSOLE.info(message);
  }

}
