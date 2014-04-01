package vrampal.connectfour.core;

import java.util.List;

public interface Game {

  String getId();

  GameStatus getStatus();

  Board getBoard();

  List<Player> getAllPlayers();

  void start();

  Player getCurrentPlayer();

  void dropDisc(int colIdx);

  Player getWinner();

}
