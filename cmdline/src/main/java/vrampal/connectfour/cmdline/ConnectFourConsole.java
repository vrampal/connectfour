package vrampal.connectfour.cmdline;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameImpl;

public class ConnectFourConsole implements Runnable {

  private static final GameMonitor CONSOLE_DISPLAY = new ConsoleDiplay();

  private static final PrintStream OUT = System.out;

  // TODO use dependency injection
  @Getter
  private final Game game = new GameImpl();

  private final PlayerInterface yellowItf;

  private final PlayerInterface redItf;

  private final Collection<GameMonitor> monitors = new ArrayList<>();

  public ConnectFourConsole(PlayerInterface yellowItf, PlayerInterface redItf, boolean enableDiplay) {
    this.yellowItf = yellowItf;
    this.redItf = redItf;

    if (enableDiplay) {
      addMonitor(CONSOLE_DISPLAY);
    }
  }

  public void addMonitor(GameMonitor monitor) {
    monitors.add(monitor);
  }

  @Override
  public void run() {
    game.begin();
    for (GameMonitor monitor : monitors) {
      monitor.begin(game);
    }

    while (game.getStatus() != GameStatus.FINISHED) {
      try {
        play();
      } catch (ConnectFourException e) {
        OUT.println(e.getMessage());
      }
    }

    Player winner = game.getWinner();
    if (winner != null) {
      for (GameMonitor monitor : monitors) {
        monitor.victory(game, winner);
      }
    } else {
      for (GameMonitor monitor : monitors) {
        monitor.drawGame(game);
      }
    }
  }

  private void play() {
    Player currentPlayer = game.getCurrentPlayer();

    int colIdx;
    if ("Yellow".equals(currentPlayer.getName())) {
      colIdx = yellowItf.selectPlayColumn(game);
    } else if ("Red".equals(currentPlayer.getName())) {
      colIdx = redItf.selectPlayColumn(game);
    } else {
      throw new IllegalStateException("Unknown player");
    }

    game.dropDisc(colIdx);

    for (GameMonitor monitor : monitors) {
      monitor.played(game, currentPlayer, colIdx);
    }
  }

}
