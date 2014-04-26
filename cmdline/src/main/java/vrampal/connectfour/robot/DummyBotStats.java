package vrampal.connectfour.robot;

import vrampal.connectfour.cmdline.ConnectFourConsole;
import vrampal.connectfour.cmdline.PlayerInterface;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

public class DummyBotStats implements Runnable {

  private static final int NB_TOTAL_GAME = 100000;

  public static void main(String[] args) {
    new DummyBotStats().run();
  }

  private int nbYellowWin = 0;

  private int nbRedWin = 0;

  private int nbDraw = 0;

  @Override
  public void run() {
    System.out.println("Running for " + NB_TOTAL_GAME + " games");

    PlayerInterface playerItf = new DummyBotPlayerInterface();

    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < NB_TOTAL_GAME; i++) {
      ConnectFourConsole consoleGame = new ConnectFourConsole(playerItf, playerItf);
      consoleGame.run();

      Game game = consoleGame.getGame();
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
