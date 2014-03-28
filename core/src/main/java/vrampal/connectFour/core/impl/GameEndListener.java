package vrampal.connectfour.core.impl;

import vrampal.connectfour.core.Player;

interface GameEndListener {

  void draw();

  void victory(Player player);

}
