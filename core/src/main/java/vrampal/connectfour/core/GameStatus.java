package vrampal.connectfour.core;

public enum GameStatus {

  /**
   * The game is available for configuration not for play.
   * Allowed operations: begin()
   */
  INIT,

  /**
   * Main game status during play.
   * Allowed operations: dropDisc()
   */
  ONGOING,

  /**
   * There is a winner or a draw, no more drop allowed.
   * Allowed operations: none
   */
  FINISHED;

}
