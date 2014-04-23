package vrampal.connectfour.cmdline;

import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.Player;

public interface GameMonitor {

  void begin(Game game);

  void played(Game game, Player prevPlayer, int prevColIdx);

  void drawGame(Game game);

  void victory(Game game, Player winner);

}
