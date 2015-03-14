package vrampal.connectfour.cmdline.robot;

import java.util.Random;

import vrampal.connectfour.cmdline.PlayerInterface;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;

public class DummyBotPlayerInterface implements PlayerInterface {

  private final Random random = new Random();

  @Override
  public int selectPlayColumn(Game game) {
    Board board = game.getBoard();
    int width = board.getWidth();

    int colIdx = random.nextInt(width);
    while (board.isColumnFull(colIdx)) {
      colIdx = random.nextInt(width);
    }

    return colIdx;
  }

}
