package vrampal.connectfour.cmdline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;

public class HumanPlayerInterface implements PlayerInterface {

  private static final PrintStream OUT = System.out;

  private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {
    PlayerInterface playerItf = new HumanPlayerInterface();
    new ConnectFourConsole(playerItf, playerItf, true).run();
  }

  @Override
  public int selectPlayColumn(Game game) {
    Board board = game.getBoard();
    int width = board.getWidth();
    int colIdx = 0;
    while ((colIdx < 1) || (colIdx > width)) {
      try {
        OUT.println("Now playing: " + game.getCurrentPlayer().getName());
        OUT.print("Type a column number [1.." + width + "]: ");
        String line = IN.readLine();
        if (line != null) {
          colIdx = Integer.parseInt(line);
        }
      } catch (IOException | NumberFormatException e) {
        OUT.println("Error while reading your input.");
      }
    }
    return colIdx - 1;
  }

}
