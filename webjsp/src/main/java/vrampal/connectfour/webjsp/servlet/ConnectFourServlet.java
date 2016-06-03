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
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameImpl;
import vrampal.connectfour.webjsp.ConnectFourRequest;
import vrampal.connectfour.webjsp.ConnectFourSession;

/**
 * Does all the main logic, creating game, handling play request.
 */
@Slf4j
@WebServlet(urlPatterns = { ConnectFourServlet.MAIN_URL })
public class ConnectFourServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public static final String MAIN_URL = "/index.html";

  public static final String MAIN_DISPLAY_JSP = "/main-display.jsp";

  public static final String PARAM_RESET_KEY = "reset";

  public static final String PARAM_PLAY_KEY = "col";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ConnectFourRequest conReq = new ConnectFourRequest(req);
    String targetPage = handleRequest(conReq);
    req.getRequestDispatcher(targetPage).forward(req, resp);
  }

  /**
   * Typed request handlers simplify testing, no need to mock http response, request dispatcher
   * or to handle exceptions.
   */
  String handleRequest(ConnectFourRequest req) {
    // Get elements from the session
    ConnectFourSession session = req.getSession();
    Game game = session.getGame();

    // Create game or reset it if necessary
    String resetStr = req.getParameter(PARAM_RESET_KEY);
    if ((game == null) || (resetStr != null)) {
      // TODO use dependency injection to avoid dependency on implementation
      game = new GameImpl();
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
        log.error(subMessage);
      } catch (ConnectFourException e) {
        subMessage = e.getMessage();
        log.error(subMessage);
      }
    }

    // Build main message
    String mainMessage = "";
    GameStatus gameStatus = game.getStatus();
    if (gameStatus == GameStatus.ONGOING) {
      Player player = game.getCurrentPlayer();
      mainMessage = "Now playing: " + player.getName();
    } else if (gameStatus == GameStatus.FINISHED) {
      Player winner = game.getWinner();
      if (winner != null) {
        mainMessage = winner.getName() + " won the game.";
      } else {
        mainMessage = "It's a draw game.";
      }
    }

    // Save request attributes for the view
    req.setMainMessage(mainMessage);
    req.setSubMessage(subMessage);
    req.setGameId(game.getId());

    // Save elements in the session
    session.setGame(game);

    return MAIN_DISPLAY_JSP;
  }

}
