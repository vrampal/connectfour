package vrampal.connectfour.cmdline;

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

public interface GameMonitor {

  void onBegin(Game game);

  void onPlay(Game game, Player player, int colIdx);

  void onDraw(Game game);

  void onVictory(Game game, Player winner);

  void onError(Game game, ConnectFourException expection);

}
