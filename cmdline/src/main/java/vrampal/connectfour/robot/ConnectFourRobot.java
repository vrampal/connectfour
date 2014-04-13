package vrampal.connectfour.robot;

import java.util.Random;

import vrampal.connectfour.cmdline.AbstractConnectFour;
import vrampal.connectfour.core.Board;

public class ConnectFourRobot extends AbstractConnectFour {

  public static void main(String[] args) {
    new ConnectFourRobot(true).run();
  }

  private static final Random rand = new Random();

  public ConnectFourRobot(boolean enableDiplay) {
    super(enableDiplay);
  }

  @Override
  protected int selectPlayColumn() {
    Board board = game.getBoard();
    int width = board.getWidth();
    int colIdx = rand.nextInt(width);
    while (board.isColumnFull(colIdx)) {
      colIdx = rand.nextInt(width);
    }
    return colIdx;
  }

}
