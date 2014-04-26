package vrampal.connectfour.robot;

import vrampal.connectfour.cmdline.GameMonitor;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

class GameDataFeeder implements GameMonitor {

  private GamesData currentData;

  GameDataFeeder(GamesData currentState) {
    this.currentData = currentState;
  }

  @Override
  public void onBegin(Game game) {
    // Nothing to do.
  }

  @Override
  public void onPlay(Game game, Player player, int colIdx) {
    int width = game.getBoard().getWidth();
    currentData = currentData.findSubGame(colIdx, width);
  }

  @Override
  public void onDraw(Game game) {
    currentData.addDraw();
  }

  @Override
  public void onVictory(Game game, Player winner) {
    String winnerName = winner.getName();
    if ("Yellow".equals(winnerName)) {
      currentData.addYellowVict();
    } else if ("Red".equals(winnerName)) {
      currentData.addRedVict();
    } else {
      throw new IllegalStateException("Unknown player");
    }
  }

  @Override
  public void onError(Game game, ConnectFourException expection) {
    // Nothing to do.
  }

}
