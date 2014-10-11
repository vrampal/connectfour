package vrampal.connectfour.cmdline.robot;

import lombok.extern.slf4j.Slf4j;
import vrampal.connectfour.cmdline.GameRunner;
import vrampal.connectfour.cmdline.PlayerInterface;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

@Slf4j
public class DummyBotStats {

  private static final int NB_TOTAL_GAME = 100000;

  public static void main(String[] args) {
    new DummyBotStats().run();
  }

  private int nbYellowWin = 0;

  private int nbRedWin = 0;

  private int nbDraw = 0;

  public void run() {
    if (log.isInfoEnabled()) {
      log.info("Running for " + NB_TOTAL_GAME + " games");
    }

    PlayerInterface playerItf = new DummyBotPlayerInterface();

    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < NB_TOTAL_GAME; i++) {
      GameRunner gameRunner = new GameRunner(playerItf, playerItf);
      gameRunner.run();

      Game game = gameRunner.getGame();
      Player winner = game.getWinner();
      analyeWinner(winner);
    }
    long endTime = System.currentTimeMillis();

    if (log.isInfoEnabled()) {
      log.info("Nb yellow win: " + nbYellowWin);
      log.info("Nb red win: " + nbRedWin);
      log.info("Nb draw: " + nbDraw);
      log.info("Elapsed time: " + (endTime - beginTime) + " ms");
    }
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
