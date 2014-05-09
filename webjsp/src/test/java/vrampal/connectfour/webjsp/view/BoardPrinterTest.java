package vrampal.connectfour.webjsp.view;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

public class BoardPrinterTest {

  // Object under test
  private BoardPrinter printer;

  @Before
  public void setUp() {
    printer = new BoardPrinter();
  }

  @Test
  public void testPrintBoard() {
    Player player = mock(Player.class);
    when(player.getName()).thenReturn("Test player");
    when(player.getLetter()).thenReturn(' ');

    Board board = mock(Board.class);
    when(board.getWidth()).thenReturn(3);
    when(board.getHeight()).thenReturn(2);
    when(board.getCell(anyInt(), anyInt())).thenReturn(player);

    Game game = mock(Game.class);
    when(game.getStatus()).thenReturn(GameStatus.ONGOING);
    when(game.getBoard()).thenReturn(board);

    String retVal = printer.printBoard(game);

    // TODO find a better assert.
    assertNotNull(retVal);
    assertNotEquals("", retVal);
  }

}
