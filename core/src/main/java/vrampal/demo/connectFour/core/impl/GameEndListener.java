package vrampal.demo.connectFour.core.impl;

import vrampal.demo.connectFour.core.Player;

interface GameEndListener {

  void draw();

  void victory(Player player);

}
