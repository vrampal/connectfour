package vrampal.connectfour.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameImpl;

public class ConnectFourHelper {

  static final String SESSION_GAME_KEY = "GAME";

  static final String PARAM_RESET_KEY = "reset";

  static final String PARAM_PLAY_KEY = "col";

  private Game game = null;

  private String mainMessage = "";

  private String subMessage = "";

  public void pageBegin(HttpServletRequest req) {

    HttpSession session = req.getSession();

    game = (Game) session.getAttribute(SESSION_GAME_KEY);

    String resetStr = req.getParameter(PARAM_RESET_KEY);
    if ((game == null) || (resetStr != null)) {
      // TODO use dependency injection
      game = new GameImpl();
      game.begin();
      session.setAttribute(SESSION_GAME_KEY, game);
    }

    String colStr = req.getParameter(PARAM_PLAY_KEY);
    if (colStr != null) {
      try {
        int colIdx = Integer.parseInt(colStr);
        game.dropDisc(colIdx - 1);
      } catch (NumberFormatException e) {
        subMessage = "Invalid parameter col: " + colStr;
      } catch (ConnectFourException e) {
        subMessage = e.getMessage();
      }
    }

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
  }

  public String getMainMessage() {
    return mainMessage;
  }

  public String getSubMessage() {
    return subMessage;
  }

  public String getGameId() {
    return game.getId();
  }

  public String printBoard() {
    StringBuilder buff = new StringBuilder(1300);

    Board board = game.getBoard();
    int height = board.getHeight();
    int width = board.getWidth();

    if (game.getStatus() == GameStatus.ONGOING) {
      buff.append("<tr>");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        buff.append("<td class=\"cfh\"><a href=\"?col=");
        buff.append(colIdx);
        buff.append("\">V</a></td>");
      }
      buff.append("</tr>");
    }

    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
      buff.append("<tr>");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        Player content = board.getCell(colIdx - 1, rowIdx - 1);
        char c = content.getLetter();
        String clazz = "cfe";
        if (c == 'R') {
          clazz = "cfr";
        } else if (c == 'Y') {
          clazz = "cfy";
        }
        buff.append("<td class=\"");
        buff.append(clazz);
        buff.append("\"></td>");
      }
      buff.append("</tr>");
    }

    return buff.toString();
  }

}
