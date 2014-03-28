package vrampal.connectFour.core;

import java.util.Collection;

public interface Game {

  String getId();

  Collection<Player> getPlayers();

  Board getBoard();

  Player getCurrentPlayer();

  void dropDisc(int colIdx);

  GameStatus getStatus();

  Player getWinner();

}
