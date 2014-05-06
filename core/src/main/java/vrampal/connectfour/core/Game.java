package vrampal.connectfour.core;

public interface Game extends GameReadOnly {

  /**
   * Change the status of a game from INIT to ONGOING.
   * Only available in status INIT.
   */
  void begin();

  /**
   * Current player drops a disc in a giver column.
   * May change status of the game from ONGOING to FINISHED.
   * Only available in status ONGOING.
   *
   * @param colIdx the column index where the disc is dropped.
   * @return the row index where the disc arrives.
   */
  int dropDisc(int colIdx);

}
