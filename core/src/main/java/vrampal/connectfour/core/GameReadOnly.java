package vrampal.connectfour.core;

import java.util.List;

/**
 * Column index goes from 0 (left) to width-1 (right).
 * Row index goes from 0 (bottom) to height-1 (top).
 */
public interface GameReadOnly {

  String getId();

  List<Player> getAllPlayers();

  Board getBoard();

  GameStatus getStatus();

  Player getCurrentPlayer();

  Player getWinner();

}
