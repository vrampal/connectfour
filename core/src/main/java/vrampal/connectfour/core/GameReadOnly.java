package vrampal.connectfour.core;

import java.io.Serializable;
import java.util.List;

/**
 * Column index goes from 0 (left) to width-1 (right).
 * Row index goes from 0 (bottom) to height-1 (top).
 */
public interface GameReadOnly extends Serializable {

  String getId();

  List<Player> getAllPlayers();

  Board getBoard();

  GameStatus getStatus();

  int getTurnNumber();

  Player getCurrentPlayer();

  Player getWinner();

}
