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
import vrampal.connectfour.webjsp.ConnectFourRequest;
import vrampal.connectfour.webjsp.ConnectFourSession;

// TODO this test could be simplified with new ConnectFourRequest design
public class ConnectFourServletTest {

  // Object under test
  private ConnectFourServlet servlet;

  private Game game;

  private HttpSession session;

  private HttpServletRequest req;

  private ConnectFourRequest conReq;

  @Before
  public void setUp() {
    servlet = new ConnectFourServlet();

    game = mock(Game.class);

    session = mock(HttpSession.class);
    when(session.getAttribute(ConnectFourSession.GAME_KEY)).thenReturn(game);

    req = mock(HttpServletRequest.class);
    when(req.getSession()).thenReturn(session);

    conReq = new ConnectFourRequest(req);
  }

  @Test
  public void testPageBeginEmptySession() {
    when(session.getAttribute(ConnectFourSession.GAME_KEY)).thenReturn(null);

    servlet.handleRequest(conReq);

    verify(session).setAttribute(eq(ConnectFourSession.GAME_KEY), anyObject());
  }

  @Test
  public void testPageBeginReset() {
    when(req.getParameter(ConnectFourServlet.PARAM_RESET_KEY)).thenReturn("bla bla bla");

    servlet.handleRequest(conReq);

    verify(session).setAttribute(eq(ConnectFourSession.GAME_KEY), anyObject());
  }

  @Test
  public void testPageBeginPlayingGame() {
    Player currentPlayer = mock(Player.class);
    when(currentPlayer.getName()).thenReturn("Test current player");
    when(game.getCurrentPlayer()).thenReturn(currentPlayer);
    when(game.getStatus()).thenReturn(GameStatus.ONGOING);
    when(req.getParameter(ConnectFourServlet.PARAM_PLAY_KEY)).thenReturn("1342");

    servlet.handleRequest(conReq);

    verify(game).dropDisc(eq(1341));
    verify(req).setAttribute(ConnectFourRequest.MAIN_MESSAGE, "Now playing: Test current player");
    verify(req).setAttribute(ConnectFourRequest.SUB_MESSAGE, "");
  }

  @Test
  public void testPageBeginVictory() {
    Player player = mock(Player.class);
    when(player.getName()).thenReturn("Test winner");
    when(game.getWinner()).thenReturn(player);
    when(game.getStatus()).thenReturn(GameStatus.FINISHED);

    servlet.handleRequest(conReq);

    verify(req).setAttribute(ConnectFourRequest.MAIN_MESSAGE, "Test winner won the game.");
    verify(req).setAttribute(ConnectFourRequest.SUB_MESSAGE, "");
  }

  @Test
  public void testPageDraw() {
    when(game.getStatus()).thenReturn(GameStatus.FINISHED);

    servlet.handleRequest(conReq);

    verify(req).setAttribute(ConnectFourRequest.MAIN_MESSAGE, "It's a draw game.");
    verify(req).setAttribute(ConnectFourRequest.SUB_MESSAGE, "");
  }

}
