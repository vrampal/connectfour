package vrampal.connectfour.robot;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;

class GamesData implements Serializable {

  @Getter(AccessLevel.PACKAGE)
  private int yellowVictCount = 0;

  @Getter(AccessLevel.PACKAGE)
  private int redVictCount = 0;

  @Getter(AccessLevel.PACKAGE)
  private int drawCount = 0;

  private GamesData[] subGames = null;

  GamesData findSubGame(int colIdx, int width) {
    if (subGames == null) {
      subGames = new GamesData[width];
    }
    GamesData subGame = subGames[colIdx - 1];
    if (subGame == null) {
      subGame = new GamesData();
      subGames[colIdx - 1] = subGame;
    }
    return subGame;
  }

  GamesData getSubGame(int colIdx, int width) {
    GamesData subGame = null;
    if (subGames != null) {
      subGame = subGames[colIdx - 1];
    }
    return subGame;
  }

  void addYellowVict() {
    yellowVictCount++;
  }

  void addRedVict() {
    redVictCount++;
  }

  void addDraw() {
    drawCount++;
  }

  int getTotalGames() {
    return yellowVictCount + redVictCount + drawCount;
  }

  double getYellowWinRate() {
    return (double) yellowVictCount / (double) getTotalGames();
  }

  double getRedWinRate() {
    return (double) redVictCount / (double) getTotalGames();
  }

  double getDrawRate() {
    return (double) drawCount / (double) getTotalGames();
  }

}
