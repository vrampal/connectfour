package vrampal.connectfour.robot;

import vrampal.connectfour.core.Player;

public class ConnectFourRobotStats implements Runnable {

  public static void main(String[] args) {
    new ConnectFourRobotStats().run();
  }

  private static final int NB_TOTAL_GAME = 1000000;

  private int nbYellowWin = 0;

  private int nbRedWin = 0;

  private int nbDraw = 0;

  @Override
  public void run() {
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < NB_TOTAL_GAME; i++) {
      ConnectFourRobot robotGame = new ConnectFourRobot(false);
      robotGame.run();

      Player winner = robotGame.getGame().getWinner();
      if (winner == null) {
        nbDraw++;
      } else if (winner.getName().equals("Yellow")) {
        nbYellowWin++;
      } else if (winner.getName().equals("Red")) {
        nbRedWin++;
      } else {
        throw new RuntimeException("Unknown winner");
      }
    }
    long endTime = System.currentTimeMillis();

    System.out.println("Nb yellow win: " + nbYellowWin);
    System.out.println("Nb red win: " + nbRedWin);
    System.out.println("Nb draw: " + nbDraw);
    System.out.println("Elapsed time: " + (endTime - beginTime) + " ms");
  }

}
