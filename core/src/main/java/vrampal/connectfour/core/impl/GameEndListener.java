package vrampal.connectfour.core.impl;

import vrampal.connectfour.core.Player;

interface GameEndListener {

  void drawGame();

  void victory(Player player);

}
