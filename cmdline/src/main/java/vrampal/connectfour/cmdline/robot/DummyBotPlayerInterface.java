package vrampal.connectfour.cmdline.robot;

import java.util.Random;

import vrampal.connectfour.cmdline.PlayerInterface;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;

public class DummyBotPlayerInterface implements PlayerInterface {

  private static final Random RAND = new Random();

  @Override
  public int selectPlayColumn(Game game) {
    Board board = game.getBoard();
    int width = board.getWidth();

    int colIdx = RAND.nextInt(width);
    while (board.isColumnFull(colIdx)) {
      colIdx = RAND.nextInt(width);
    }

    return colIdx;
  }

}
