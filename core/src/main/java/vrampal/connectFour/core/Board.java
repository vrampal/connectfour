package vrampal.connectFour.core;

/**
 * 2D board implementation, 3D is not (yet) supported.
 * Column index goes from 0 (left) to width-1 (right).
 * Row index goes from 0 (bottom) to height-1 (top).
 */
public interface Board {

  int getWidth();

  int getHeight();

  Player getCell(int colIdx, int rowIdx);

}
