package vrampal.connectfour.webjsp.servlet;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.webjsp.ConnectFourSession;
import vrampal.connectfour.webjsp.RequestAttributes;

public class ConnectFourServletTest {

  // Object under test
  private ConnectFourServlet servlet;

  private Game game;

  private HttpSession session;

  private HttpServletRequest req;

  @Before
  public void setUp() {
    servlet = new ConnectFourServlet();

    game = mock(Game.class);

    session = mock(HttpSession.class);
    when(session.getAttribute(ConnectFourSession.GAME_KEY)).thenReturn(game);

    req = mock(HttpServletRequest.class);
    when(req.getSession()).thenReturn(session);
  }

  @Test
  public void testPageBeginEmptySession() {
    when(session.getAttribute(ConnectFourSession.GAME_KEY)).thenReturn(null);

    servlet.handleRequest(req);

    verify(session).setAttribute(eq(ConnectFourSession.GAME_KEY), anyObject());
  }

  @Test
  public void testPageBeginReset() {
    when(req.getParameter(ConnectFourServlet.PARAM_RESET_KEY)).thenReturn("bla bla bla");

    servlet.handleRequest(req);

    verify(session).setAttribute(eq(ConnectFourSession.GAME_KEY), anyObject());
  }

  @Test
  public void testPageBeginPlayingGame() {
    Player currentPlayer = mock(Player.class);
    when(currentPlayer.getName()).thenReturn("Test current player");
    when(game.getCurrentPlayer()).thenReturn(currentPlayer);
    when(game.getStatus()).thenReturn(GameStatus.ONGOING);
    when(req.getParameter(ConnectFourServlet.PARAM_PLAY_KEY)).thenReturn("1342");

    servlet.handleRequest(req);

    verify(game).dropDisc(eq(1341));
    verify(req).setAttribute(RequestAttributes.MAIN_MESSAGE, "Now playing: Test current player");
    verify(req).setAttribute(RequestAttributes.SUB_MESSAGE, "");
  }

  @Test
  public void testPageBeginVictory() {
    Player player = mock(Player.class);
    when(player.getName()).thenReturn("Test winner");
    when(game.getWinner()).thenReturn(player);
    when(game.getStatus()).thenReturn(GameStatus.FINISHED);

    servlet.handleRequest(req);

    verify(req).setAttribute(RequestAttributes.MAIN_MESSAGE, "Test winner won the game.");
    verify(req).setAttribute(RequestAttributes.SUB_MESSAGE, "");
  }

  @Test
  public void testPageDraw() {
    when(game.getStatus()).thenReturn(GameStatus.FINISHED);

    servlet.handleRequest(req);

    verify(req).setAttribute(RequestAttributes.MAIN_MESSAGE, "It's a draw game.");
    verify(req).setAttribute(RequestAttributes.SUB_MESSAGE, "");
  }

}
