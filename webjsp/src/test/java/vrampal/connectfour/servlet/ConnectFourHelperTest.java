package vrampal.connectfour.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

public class ConnectFourHelperTest {

  // Object under test
  private ConnectFourHelper helper;

  private Player player;

  private Game game;

  private HttpSession session;

  private HttpServletRequest req;

  @Before
  public void setUp() throws Exception {
    helper = new ConnectFourHelper();

    player = mock(Player.class);
    when(player.getName()).thenReturn("Test player");
    when(player.getLetter()).thenReturn('T');

    Board board = mock(Board.class);
    when(board.getWidth()).thenReturn(3);
    when(board.getHeight()).thenReturn(2);
    when(board.getCell(anyInt(), anyInt())).thenReturn(player);

    game = mock(Game.class);
    when(game.getId()).thenReturn("Test game");
    when(game.getStatus()).thenReturn(GameStatus.ONGOING);
    when(game.getBoard()).thenReturn(board);
    when(game.getCurrentPlayer()).thenReturn(player);

    session = mock(HttpSession.class);
    when(session.getAttribute(ConnectFourHelper.SESSION_GAME_KEY)).thenReturn(game);

    req = mock(HttpServletRequest.class);
    when(req.getSession()).thenReturn(session);
  }

  @Test
  public void testPageBeginEmptySession() {
    when(session.getAttribute(ConnectFourHelper.SESSION_GAME_KEY)).thenReturn(null);

    helper.pageBegin(req);

    verify(session).setAttribute(eq(ConnectFourHelper.SESSION_GAME_KEY), anyObject());
  }

  @Test
  public void testPageBeginReset() {
    when(req.getParameter(ConnectFourHelper.PARAM_RESET_KEY)).thenReturn("bla bla bla");

    helper.pageBegin(req);

    verify(session).setAttribute(eq(ConnectFourHelper.SESSION_GAME_KEY), anyObject());
  }

  @Test
  public void testPageBeginPlayingGame() {
    when(req.getParameter(ConnectFourHelper.PARAM_PLAY_KEY)).thenReturn("1342");

    helper.pageBegin(req);

    verify(game).dropDisc(eq(1341));
  }

  @Test
  public void testPageBeginVictory() {
    when(game.getStatus()).thenReturn(GameStatus.FINISHED);
    when(game.getWinner()).thenReturn(player);

    helper.pageBegin(req);

    assertEquals("Test game", helper.getGameId());
    assertEquals("Test player won the game.", helper.getMainMessage());
  }

  @Test
  public void testPageDraw() {
    when(game.getStatus()).thenReturn(GameStatus.FINISHED);

    helper.pageBegin(req);

    assertEquals("Test game", helper.getGameId());
    assertEquals("It's a draw game.", helper.getMainMessage());
  }

  @Test
  public void testPrintBoard() {
    helper.pageBegin(req);

    String result = helper.printBoard();

    // TODO find a better assert.
    assertNotNull(result);
  }

}
