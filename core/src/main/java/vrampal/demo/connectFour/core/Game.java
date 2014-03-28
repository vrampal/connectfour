package vrampal.demo.connectFour.core;

import java.awt.Color;

public class Game implements VictoryListener {

  private final Board board;

  private final Player[] players;

  public Game() {
    board = new Board(this);
    players = new Player[2];
    players[0] = new Player("Player 1", 'Y', Color.YELLOW);
    players[1] = new Player("Player 2", 'R', Color.RED);
  }

}
