package vrampal.connectfour.cmdline.robot;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import vrampal.connectfour.cmdline.GameRunner;
import vrampal.connectfour.cmdline.PlayerInterface;

@Slf4j
public class LearningBotStats {

  private static final int NB_TOTAL_GAME = 100000;

  public static void main(String[] args) {
    new LearningBotStats().run();
  }

  public void run() {
    if (log.isInfoEnabled()) {
      log.info("Running for " + NB_TOTAL_GAME + " games");
    }

    GamesData rootData = new GamesData();
    PlayerInterface playerItf = new DummyBotPlayerInterface();

    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < NB_TOTAL_GAME; i++) {
      GameRunner consoleGame = new GameRunner(playerItf, playerItf);
      GameDataFeeder dataFeed = new GameDataFeeder(rootData);
      consoleGame.addMonitor(dataFeed);
      consoleGame.run();
    }
    long endTime = System.currentTimeMillis();

    rootData.computeFullStats();

    try {
      rootData.saveToDisk("LearningBotData.tmp");
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    if (log.isInfoEnabled()) {
      log.info("Nb yellow win: " + rootData.getYellowVictCount());
      log.info("Nb red win: " + rootData.getRedVictCount());
      log.info("Nb draw: " + rootData.getDrawCount());
      log.info("Elapsed time: " + (endTime - beginTime) + " ms");
    }
  }

}
