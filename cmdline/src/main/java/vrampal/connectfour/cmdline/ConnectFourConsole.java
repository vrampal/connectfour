package vrampal.connectfour.cmdline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import vrampal.connectfour.core.Board;

public class ConnectFourConsole extends AbstractConnectFour implements Runnable {

  public static void main(String[] args) {
    new ConnectFourConsole(true).run();
  }

  private final BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));

  public ConnectFourConsole(boolean enableDiplay) {
    super(enableDiplay);
  }

  @Override
  protected int selectPlayColumn() {
    Board board = game.getBoard();
    int width = board.getWidth();
    int colIdx = 0;
    while ((colIdx < 1) || (colIdx > width)) {
      try {
        out.println("Now playing: " + game.getCurrentPlayer().getName());
        out.print("Type a column number [1.." + width + "]: ");
        String line = buffRead.readLine();
        if (line != null) {
          colIdx = Integer.parseInt(line);
        }
      } catch (IOException | NumberFormatException e) {
        out.println("Error while reading your input.");
      }
    }
    return colIdx - 1;
  }

}
