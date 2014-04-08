package vrampal.connectfour.core;


public interface Game extends GameReadOnly {

  void begin();

  void dropDisc(int colIdx);

}
