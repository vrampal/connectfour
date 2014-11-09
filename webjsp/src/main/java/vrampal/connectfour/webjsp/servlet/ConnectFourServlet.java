package vrampal.connectfour.webjsp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameFactory;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameFactoryImpl;
import vrampal.connectfour.webjsp.ConnectFourSession;
import vrampal.connectfour.webjsp.RequestAttributes;

/**
 * Does all the main logic, creating game, handling play request.
 */
@Slf4j
@WebServlet(urlPatterns = { ConnectFourServlet.MAIN_URL })
public class ConnectFourServlet extends HttpServlet {

  public static final String MAIN_URL = "/index.html";

  public static final String MAIN_DISPLAY_JSP = "/main-display.jsp";

  public static final String PARAM_RESET_KEY = "reset";

  public static final String PARAM_PLAY_KEY = "col";

  // TODO use dependency injection to avoid dependency on implementation
  private static final GameFactory GAME_FACTORY = GameFactoryImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String targetPage = handleRequest(req);
    req.getRequestDispatcher(targetPage).forward(req, resp);
  }

  /**
   * This method simplify testing, no need to mock response, request dispatcher
   * or to handle exceptions.
   */
  String handleRequest(HttpServletRequest req) {
    // Get elements from the session
    ConnectFourSession session = new ConnectFourSession(req.getSession());
    Game game = session.getGame();

    // Create game or reset it if necessary
    String resetStr = req.getParameter(PARAM_RESET_KEY);
    if ((game == null) || (resetStr != null)) {
      // TODO use dependency injection to avoid dependency on implementation
      game = GAME_FACTORY.createGame();
      game.begin();
    }

    // Handle play parameter
    String subMessage = "";
    String colStr = req.getParameter(PARAM_PLAY_KEY);
    if (colStr != null) {
      try {
        int colIdx = Integer.parseInt(colStr);
        game.dropDisc(colIdx - 1);
      } catch (NumberFormatException e) {
        subMessage = "Invalid parameter col: " + colStr;
        log.error("Invalid column", e);
      } catch (ConnectFourException e) {
        subMessage = e.getMessage();
        log.error("Unable to play", e);
      }
    }

    // Build main message
    String mainMessage = "";
    if (game.getStatus() == GameStatus.ONGOING) {
      Player player = game.getCurrentPlayer();
      mainMessage = "Now playing: " + player.getName();
    } else if (game.getStatus() == GameStatus.FINISHED) {
      Player winner = game.getWinner();
      if (winner != null) {
        mainMessage = winner.getName() + " won the game.";
      } else {
        mainMessage = "It's a draw game.";
      }
    }

    // Save request attributes for the view
    req.setAttribute(RequestAttributes.MAIN_MESSAGE, mainMessage);
    req.setAttribute(RequestAttributes.SUB_MESSAGE, subMessage);
    req.setAttribute(RequestAttributes.GAME_ID, game.getId());

    // Save elements in the session
    session.setGame(game);

    return MAIN_DISPLAY_JSP;
  }

}
