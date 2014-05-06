package vrampal.connectfour.cmdline.robot;

import java.io.IOException;
import java.io.PrintStream;

import vrampal.connectfour.cmdline.GameRunner;
import vrampal.connectfour.cmdline.PlayerInterface;

public class LearningBotStats implements Runnable {

  private static final PrintStream OUT = System.out;

  private static final int NB_TOTAL_GAME = 100000;

  public static void main(String[] args) {
    new LearningBotStats().run();
  }

  @Override
  public void run() {
    OUT.println("Running for " + NB_TOTAL_GAME + " games");

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
      OUT.println(e.getMessage());
    }

    OUT.println("Nb yellow win: " + rootData.getYellowVictCount());
    OUT.println("Nb red win: " + rootData.getRedVictCount());
    OUT.println("Nb draw: " + rootData.getDrawCount());
    OUT.println("Elapsed time: " + (endTime - beginTime) + " ms");
  }

}
