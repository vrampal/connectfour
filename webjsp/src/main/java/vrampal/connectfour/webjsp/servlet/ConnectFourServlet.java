package vrampal.connectfour.webjsp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameFactory;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameFactoryImpl;
import vrampal.connectfour.webjsp.RequestAttributeKeys;
import vrampal.connectfour.webjsp.SessionKeys;

/**
 * Does all the main logic, creating game, handling play request.
 */
@Slf4j
@WebServlet(urlPatterns = { ConnectFourServlet.MAIN_URL, "/index.jsp" })
public class ConnectFourServlet extends HttpServlet implements SessionKeys, RequestAttributeKeys {

  private static final long serialVersionUID = -30750880358249276L;

  public static final String MAIN_URL = "/index.html";

  public static final String PARAM_RESET_KEY = "reset";

  public static final String PARAM_PLAY_KEY = "col";

  // TODO use dependency injection to avoid dependency on implementation
  private static final GameFactory GAME_FACTORY = GameFactoryImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    handleRequest(req);
    req.getRequestDispatcher("/main-display.jsp").forward(req, resp);
  }

  /**
   * This method simplify testing, no need to mock response, request dispatcher
   * or to handle exceptions.
   */
  void handleRequest(HttpServletRequest req) {
    // Get elements from the session
    HttpSession session = req.getSession();
    Game game = (Game) session.getAttribute(SESSION_GAME_KEY);

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
    req.setAttribute(ATTR_MAIN_MESSAGE_KEY, mainMessage);
    req.setAttribute(ATTR_SUB_MESSAGE_KEY, subMessage);
    req.setAttribute(ATTR_GAME_ID_KEY, game.getId());

    // Save elements in the session
    session.setAttribute(SESSION_GAME_KEY, game);
  }

}
