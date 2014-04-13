package vrampal.connectfour.robot;

import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

public class ConnectFourRobotStats implements Runnable {

  public static void main(String[] args) {
    new ConnectFourRobotStats().run();
  }

  private static final int NB_TOTAL_GAME = 100000;

  private int nbYellowWin = 0;

  private int nbRedWin = 0;

  private int nbDraw = 0;

  @Override
  public void run() {
    System.out.println("Running for " + NB_TOTAL_GAME + " games");

    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < NB_TOTAL_GAME; i++) {
      ConnectFourRobot robotRunner = new ConnectFourRobot(false);
      robotRunner.run();

      Game game = robotRunner.getGame();
      Player winner = game.getWinner();
      analyeWinner(winner);
    }
    long endTime = System.currentTimeMillis();

    System.out.println("Nb yellow win: " + nbYellowWin);
    System.out.println("Nb red win: " + nbRedWin);
    System.out.println("Nb draw: " + nbDraw);
    System.out.println("Elapsed time: " + (endTime - beginTime) + " ms");
  }

  private void analyeWinner(Player winner) {
    if (winner == null) {
      nbDraw++;
    } else {
      String winnerName = winner.getName();
      if ("Yellow".equals(winnerName)) {
        nbYellowWin++;
      } else if ("Red".equals(winnerName)) {
        nbRedWin++;
      } else {
        throw new IllegalStateException("Unknown winner");
      }
    }
  }

}
