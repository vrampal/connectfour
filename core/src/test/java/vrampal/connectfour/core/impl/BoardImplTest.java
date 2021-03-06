package vrampal.connectfour.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Player;

/**
 * Simple JUnit with Mockito.
 *
 * TODO a test showing victory on last drop of a full board should be nice.
 */
public class BoardImplTest {

  // Object under test
  private BoardImpl board;

  @Before
  public void setUp() {
    board = new BoardImpl();
  }

  // ----- No player tests -----

  @Test
  public void testBoardImpl() {
    assertEquals(7, board.getWidth());
    assertEquals(6, board.getHeight());

    for (int colIdx = 0; colIdx < 7; colIdx++) {
      for (int rowIdx = 0; rowIdx < 6; rowIdx++) {
        assertSame(BoardImpl.EMPTY_PLAYER, board.getCell(colIdx, rowIdx));
      }
    }
  }

  @Test
  public void testBoardImplIntInt() {
    board = new BoardImpl(15, 10);

    assertEquals(15, board.getWidth());
    assertEquals(10, board.getHeight());

    for (int colIdx = 0; colIdx < 15; colIdx++) {
      for (int rowIdx = 0; rowIdx < 10; rowIdx++) {
        assertSame(BoardImpl.EMPTY_PLAYER, board.getCell(colIdx, rowIdx));
      }
    }
  }

  @Test(expected = ConnectFourException.class)
  public void testBoardImplIntIntInvalid1() {
    board = new BoardImpl(-2, 8);
  }

  @Test(expected = ConnectFourException.class)
  public void testBoardImplIntIntInvalid2() {
    board = new BoardImpl(3, 0);
  }

  @Test
  public void testGetEmptyPlayer() {
    Player emptyPlayer = board.getEmptyPlayer();
    assertEquals(BoardImpl.EMPTY_PLAYER, emptyPlayer);
    assertNotNull(emptyPlayer);
  }

  @Test(expected = ConnectFourException.class)
  public void testGetCellInvalid1() {
    board.getCell(-8, 2);
  }

  @Test(expected = ConnectFourException.class)
  public void testGetCellInvalid2() {
    board.getCell(192, 2);
  }

  @Test(expected = ConnectFourException.class)
  public void testGetCellInvalid3() {
    board.getCell(3, -14);
  }

  @Test(expected = ConnectFourException.class)
  public void testGetCellInvalid4() {
    board.getCell(3, 1234);
  }

  @Test(expected = ConnectFourException.class)
  public void testDropDiscNull() {
    board.dropDisc(null, 3);
  }

  @Test(expected = ConnectFourException.class)
  public void testDropDiscEmptyPlayer() {
    board.dropDisc(BoardImpl.EMPTY_PLAYER, 3);
  }

  // ----- One player tests -----

  @Test
  public void testDropDisc() {
    Player player1 = mock(Player.class);
    GameEndListener endGameListener = mock(GameEndListener.class);
    board.setEndGameListener(endGameListener);

    board.dropDisc(player1, 3);
    assertSame(player1, board.getCell(3, 0));

    board.dropDisc(player1, 0);
    assertSame(player1, board.getCell(0, 0));

    board.dropDisc(player1, 3);
    assertSame(player1, board.getCell(3, 1));

    board.dropDisc(player1, 2);
    assertSame(player1, board.getCell(2, 0));

    board.dropDisc(player1, 3);
    assertSame(player1, board.getCell(3, 2));

    board.dropDisc(player1, 6);
    assertSame(player1, board.getCell(6, 0));

    verifyZeroInteractions(endGameListener);
  }

  // ----- Two players tests -----

  @Test(expected = ConnectFourException.class)
  public void testDropDiscFull() {
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);

    board.dropDisc(player1, 3);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 3);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 3);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 3);
  }

  @Test
  public void testUpToVictoryHorizontal() {
    Player player1 = mock(Player.class);
    GameEndListener endGameListener = mock(GameEndListener.class);
    board.setEndGameListener(endGameListener);

    board.dropDisc(player1, 4);
    board.dropDisc(player1, 5);
    board.dropDisc(player1, 2);
    board.dropDisc(player1, 3);

    verify(endGameListener).victory(player1);
    verify(endGameListener, never()).drawGame();
  }

  @Test
  public void testUpToVictoryVertical() {
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    GameEndListener endGameListener = mock(GameEndListener.class);
    board.setEndGameListener(endGameListener);

    board.dropDisc(player2, 3);
    board.dropDisc(player1, 3);
    board.dropDisc(player1, 3);
    board.dropDisc(player1, 3);
    board.dropDisc(player1, 3);

    verify(endGameListener).victory(player1);
    verify(endGameListener, never()).drawGame();
  }

  @Test
  public void testUpToVictoryDiaginal1() {
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    GameEndListener endGameListener = mock(GameEndListener.class);
    board.setEndGameListener(endGameListener);

    board.dropDisc(player1, 4);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 5);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 3);
    board.dropDisc(player2, 2);
    board.dropDisc(player1, 2);
    board.dropDisc(player2, 2);
    board.dropDisc(player1, 2);
    board.dropDisc(player2, 5);
    board.dropDisc(player1, 4);

    verify(endGameListener).victory(player1);
    verify(endGameListener, never()).victory(player2);
    verify(endGameListener, never()).drawGame();
  }

  @Test
  public void testUpToVictoryDiaginal2() {
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    GameEndListener endGameListener = mock(GameEndListener.class);
    board.setEndGameListener(endGameListener);

    board.dropDisc(player1, 2);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 1);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 3);
    board.dropDisc(player2, 4);
    board.dropDisc(player1, 4);
    board.dropDisc(player2, 4);
    board.dropDisc(player1, 4);
    board.dropDisc(player2, 1);
    board.dropDisc(player1, 2);

    verify(endGameListener).victory(player1);
    verify(endGameListener, never()).victory(player2);
    verify(endGameListener, never()).drawGame();
  }

  @Test
  public void testUpToDraw() {
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    GameEndListener endGameListener = mock(GameEndListener.class);
    board.setEndGameListener(endGameListener);

    board.dropDisc(player1, 0);
    board.dropDisc(player2, 1);
    board.dropDisc(player1, 2);
    board.dropDisc(player1, 3);
    board.dropDisc(player1, 4);
    board.dropDisc(player2, 5);
    board.dropDisc(player2, 6);

    board.dropDisc(player2, 0);
    board.dropDisc(player1, 1);
    board.dropDisc(player2, 2);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 4);
    board.dropDisc(player1, 5);
    board.dropDisc(player1, 6);

    board.dropDisc(player1, 0);
    board.dropDisc(player2, 1);
    board.dropDisc(player1, 2);
    board.dropDisc(player1, 3);
    board.dropDisc(player1, 4);
    board.dropDisc(player2, 5);
    board.dropDisc(player2, 6);

    board.dropDisc(player2, 0);
    board.dropDisc(player1, 1);
    board.dropDisc(player2, 2);
    board.dropDisc(player2, 3);
    board.dropDisc(player2, 4);
    board.dropDisc(player1, 5);
    board.dropDisc(player1, 6);

    board.dropDisc(player1, 0);
    board.dropDisc(player2, 1);
    board.dropDisc(player2, 2);
    board.dropDisc(player1, 3);
    board.dropDisc(player1, 4);
    board.dropDisc(player2, 5);
    board.dropDisc(player2, 6);

    board.dropDisc(player2, 0);
    board.dropDisc(player1, 1);
    board.dropDisc(player2, 2);
    board.dropDisc(player2, 3);
    board.dropDisc(player1, 4);
    board.dropDisc(player1, 5);
    board.dropDisc(player2, 6);

    verify(endGameListener, never()).victory(player1);
    verify(endGameListener, never()).victory(player2);
    verify(endGameListener).drawGame();
  }

}
