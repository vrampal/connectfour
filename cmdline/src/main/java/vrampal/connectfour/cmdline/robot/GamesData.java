package vrampal.connectfour.cmdline.robot;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;

class GamesData implements Serializable {

  private static final long serialVersionUID = -482391667893743911L;

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
    GamesData subGame = subGames[colIdx];
    if (subGame == null) {
      subGame = new GamesData();
      subGames[colIdx] = subGame;
    }
    return subGame;
  }

  GamesData getSubGame(int colIdx) {
    GamesData subGame = null;
    if (subGames != null) {
      subGame = subGames[colIdx];
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

  void computeFullStats() {
    if (subGames != null) {
      yellowVictCount = 0;
      redVictCount = 0;
      drawCount = 0;
      for (GamesData subGame : subGames) {
        if (subGame != null) {
          subGame.computeFullStats();
          yellowVictCount += subGame.getYellowVictCount();
          redVictCount += subGame.getRedVictCount();
          drawCount += subGame.getDrawCount();
        }
      }
    }
  }

  @Override
  public String toString() {
    return String.format("Yellow: %2.1f - Red: %2.1f - Draw: %2.1f", getYellowWinRate() * 100.0,
        getRedWinRate() * 100.0, getDrawRate() * 100.0);
  }

  void saveToDisk(String fileName) throws IOException {
    FileOutputStream foStream = new FileOutputStream(fileName);
    ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
    ooStream.writeObject(this);
    ooStream.close();
  }

  static GamesData loadFromDisk(String fileName) throws IOException, ClassNotFoundException {
    FileInputStream fiStream = new FileInputStream(fileName);
    ObjectInputStream oiStream = new ObjectInputStream(fiStream);
    GamesData gamesData = (GamesData) oiStream.readObject();
    oiStream.close();
    return gamesData;
  }

}
