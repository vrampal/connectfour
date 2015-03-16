package vrampal.connectfour.webjsp.servlet;

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

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.webjsp.ConnectFourRequest;
import vrampal.connectfour.webjsp.ConnectFourSession;

// TODO this test could be simplified with new ConnectFourRequest design
public class ConnectFourServletTest {

  // Object under test
  private ConnectFourServlet servlet;

  private Player player;

  private Game game;

  private HttpSession session;

  private HttpServletRequest req;

  private ConnectFourRequest conReq;

  @Before
  public void setUp() {
    servlet = new ConnectFourServlet();

    player = mock(Player.class);
    when(player.getName()).thenReturn("Test player");

    game = mock(Game.class);

    session = mock(HttpSession.class);
    when(session.getAttribute(ConnectFourSession.GAME_KEY)).thenReturn(game);

    req = mock(HttpServletRequest.class);
    when(req.getSession()).thenReturn(session);

    conReq = new ConnectFourRequest(req);
  }

  @Test
  public void testHandleRequestEmptySession() {
    when(session.getAttribute(ConnectFourSession.GAME_KEY)).thenReturn(null);

    servlet.handleRequest(conReq);

    verify(session).setAttribute(eq(ConnectFourSession.GAME_KEY), anyObject());
  }

  @Test
  public void testHandleRequestReset() {
    when(req.getParameter(ConnectFourServlet.PARAM_RESET_KEY)).thenReturn("defined");

    servlet.handleRequest(conReq);

    verify(session).setAttribute(eq(ConnectFourSession.GAME_KEY), anyObject());
  }

  @Test
  public void testHandleRequestOngoingGame() {
    when(game.getStatus()).thenReturn(GameStatus.ONGOING);
    when(game.getCurrentPlayer()).thenReturn(player);

    when(req.getParameter(ConnectFourServlet.PARAM_PLAY_KEY)).thenReturn("1342");

    servlet.handleRequest(conReq);

    verify(game).dropDisc(eq(1341));
    verify(req).setAttribute(ConnectFourRequest.MAIN_MESSAGE, "Now playing: Test player");
    verify(req).setAttribute(ConnectFourRequest.SUB_MESSAGE, "");
  }

  @Test
  public void testHandleRequestInvalidColumn() {
    when(game.getStatus()).thenReturn(GameStatus.ONGOING);
    when(game.getCurrentPlayer()).thenReturn(player);

    when(req.getParameter(ConnectFourServlet.PARAM_PLAY_KEY)).thenReturn("invalid value");

    servlet.handleRequest(conReq);

    verify(req).setAttribute(ConnectFourRequest.MAIN_MESSAGE, "Now playing: Test player");
    verify(req).setAttribute(ConnectFourRequest.SUB_MESSAGE, "Invalid parameter col: invalid value");
  }

  @Test
  public void testHandleRequestInvalidPlay() {
    when(game.getStatus()).thenReturn(GameStatus.ONGOING);
    when(game.getCurrentPlayer()).thenReturn(player);
    when(game.dropDisc(anyInt())).thenThrow(new ConnectFourException("Invalid play"));

    when(req.getParameter(ConnectFourServlet.PARAM_PLAY_KEY)).thenReturn("1342");

    servlet.handleRequest(conReq);

    verify(game).dropDisc(eq(1341));
    verify(req).setAttribute(ConnectFourRequest.MAIN_MESSAGE, "Now playing: Test player");
    verify(req).setAttribute(ConnectFourRequest.SUB_MESSAGE, "Invalid play");
  }

  @Test
  public void testHandleRequestVictory() {
    when(game.getStatus()).thenReturn(GameStatus.FINISHED);
    when(game.getWinner()).thenReturn(player);

    servlet.handleRequest(conReq);

    verify(req).setAttribute(ConnectFourRequest.MAIN_MESSAGE, "Test player won the game.");
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
