package vrampal.connectFour.core.impl;

import vrampal.connectFour.core.Player;

interface GameEndListener {

  void draw();

  void victory(Player player);

}
