package vrampal.connectfour.core.impl;

import java.io.Serializable;

import vrampal.connectfour.core.Player;

interface GameEndListener extends Serializable {

  void drawGame();

  void victory(Player winner);

}
