package vrampal.connectfour.webjsp.view;

import javax.servlet.http.HttpSession;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.webjsp.SessionKeys;

/**
 * Used by main-display.jsp to print the board.
 */
public class BoardPrinter implements SessionKeys {

  public String printBoard(HttpSession session) {
    String retVal = "";
    Object candidate = session.getAttribute(SESSION_GAME_KEY);
    // Protects against null and ClassCastException
    if (candidate instanceof Game) {
      retVal = printBoard((Game) candidate);
    }
    return retVal;
  }

  String printBoard(Game game) {
    Board board = game.getBoard();
    int height = board.getHeight();
    int width = board.getWidth();
    GameStatus status = game.getStatus();

    StringBuilder buff = new StringBuilder(1300);

    if (status == GameStatus.ONGOING) {
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
