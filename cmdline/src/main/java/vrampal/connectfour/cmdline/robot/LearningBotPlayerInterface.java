package vrampal.connectfour.cmdline.robot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vrampal.connectfour.cmdline.ConsoleDiplay;
import vrampal.connectfour.cmdline.GameMonitor;
import vrampal.connectfour.cmdline.GameRunner;
import vrampal.connectfour.cmdline.HumanPlayerInterface;
import vrampal.connectfour.cmdline.PlayerInterface;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameReadOnly;
import vrampal.connectfour.core.Player;

/**
 * Warning the learning-bot is totally broken by design.
 */
class LearningBotPlayerInterface implements PlayerInterface, GameMonitor {

  private static final Random RAND = new Random();

  public static void main(String[] args) throws ClassNotFoundException, IOException {
    ConsoleDiplay consoleDisplay = new ConsoleDiplay();
    HumanPlayerInterface player1Itf = new HumanPlayerInterface();

    GamesData rootData = GamesData.loadFromDisk("LearningBotData.tmp");
    LearningBotPlayerInterface player2Itf = new LearningBotPlayerInterface("Red", rootData);

    GameRunner consoleGame = new GameRunner(player1Itf, player2Itf);
    consoleGame.addMonitor(player2Itf);
    consoleGame.addMonitor(consoleDisplay);

    consoleGame.run();
  }

  private final String robotPlayerName;

  private final GamesData rootData;

  private GamesData currentData = null;

  LearningBotPlayerInterface(String robotPlayerName, GamesData rootData) {
    this.robotPlayerName = robotPlayerName;
    this.rootData = rootData;
  }

  @Override
  public void onBegin(GameReadOnly game) {
    currentData = rootData;
  }

  @Override
  public void onPlay(GameReadOnly game, Player player, int colIdx, int rowIdx) {
    int width = game.getBoard().getWidth();
    currentData = currentData.findSubGame(colIdx, width);
  }

  @Override
  public void onDraw(GameReadOnly game) {
    // Nothing to do.
  }

  @Override
  public void onVictory(GameReadOnly game, Player winner) {
    // Nothing to do.
  }

  @Override
  public void onError(GameReadOnly game, ConnectFourException expection) {
    // Nothing to do.
  }

  @Override
  public int selectPlayColumn(Game game) {
    Board board = game.getBoard();
    int width = board.getWidth();
    List<Integer> bestMatchs = new ArrayList<>();
    double bestStat = -1000.0;

    for (int colIdx = 0; colIdx < width; colIdx++) {
      if (!board.isColumnFull(colIdx)) {
        GamesData candidate = currentData.getSubGame(colIdx);
        if (candidate != null) {
          double stat = getStat(candidate);
          if (stat > bestStat) {
            bestStat = stat;
            bestMatchs.clear();
            bestMatchs.add(colIdx);
          } else if (stat == bestStat) {
            bestMatchs.add(colIdx);
          }
        }
      }
    }

    int randIdx = 0;
    if (bestMatchs.size() > 1) {
      randIdx = RAND.nextInt(bestMatchs.size());
    }

    return bestMatchs.get(randIdx);
  }

  private double getStat(GamesData candidate) {
    double stat;
    if ("Yellow".equals(robotPlayerName)) {
      stat = candidate.getYellowWinRate();
    } else if ("Red".equals(robotPlayerName)) {
      stat = candidate.getRedWinRate();
    } else {
      throw new IllegalStateException("Unknown robot name");
    }
    return stat;
  }

}
