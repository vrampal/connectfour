package vrampal.connectfour.cmdline;

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.GameReadOnly;
import vrampal.connectfour.core.Player;

public interface GameMonitor {

  void onBegin(GameReadOnly game);

  void onPlay(GameReadOnly game, Player player, int colIdx, int rowIdx);

  void onDraw(GameReadOnly game);

  void onVictory(GameReadOnly game, Player winner);

  void onError(GameReadOnly game, ConnectFourException expection);

}
