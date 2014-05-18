package vrampal.connectfour.cmdline;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameFactory;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameFactoryImpl;

public class GameRunner {

  // TODO use dependency injection to avoid dependency on implementation
  private static final GameFactory GAME_FACTORY = GameFactoryImpl.getInstance();

  @Getter
  private final Game game;

  private final PlayerInterface yellowItf;

  private final PlayerInterface redItf;

  private final Collection<GameMonitor> monitors = new ArrayList<>();

  public GameRunner(Game game, PlayerInterface yellowItf, PlayerInterface redItf) {
    this.game = game;
    this.yellowItf = yellowItf;
    this.redItf = redItf;
  }

  public GameRunner(PlayerInterface yellowItf, PlayerInterface redItf) {
    this(GAME_FACTORY.createGame(), yellowItf, redItf);
  }

  public void addMonitor(GameMonitor monitor) {
    monitors.add(monitor);
  }

  public void run() {
    game.begin();
    for (GameMonitor monitor : monitors) {
      monitor.onBegin(game);
    }

    while (game.getStatus() != GameStatus.FINISHED) {
      try {
        playOneTurn();
      } catch (ConnectFourException e) {
        for (GameMonitor monitor : monitors) {
          monitor.onError(game, e);
        }
      }
    }

    Player winner = game.getWinner();
    if (winner != null) {
      for (GameMonitor monitor : monitors) {
        monitor.onVictory(game, winner);
      }
    } else {
      for (GameMonitor monitor : monitors) {
        monitor.onDraw(game);
      }
    }
  }

  private void playOneTurn() {
    Player currentPlayer = game.getCurrentPlayer();
    String curPlayerName = currentPlayer.getName();

    int colIdx;
    if ("Yellow".equals(curPlayerName)) {
      colIdx = yellowItf.selectPlayColumn(game);
    } else if ("Red".equals(curPlayerName)) {
      colIdx = redItf.selectPlayColumn(game);
    } else {
      throw new IllegalStateException("Unknown player");
    }

    int rowIdx = game.dropDisc(colIdx);

    for (GameMonitor monitor : monitors) {
      monitor.onPlay(game, currentPlayer, colIdx, rowIdx);
    }
  }

}
