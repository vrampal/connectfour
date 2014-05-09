package vrampal.connectfour.webjsp.view;

import java.io.IOException;
import java.io.Writer;

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

  public void printBoard(HttpSession session, Writer out) throws IOException {
    Object candidate = session.getAttribute(SESSION_GAME_KEY);
    // Protects against null and ClassCastException
    if (candidate instanceof Game) {
      printBoard((Game) candidate, out);
    }
  }

  void printBoard(Game game, Writer out) throws IOException {
    Board board = game.getBoard();
    int height = board.getHeight();
    int width = board.getWidth();
    GameStatus status = game.getStatus();

    if (status == GameStatus.ONGOING) {
      out.write("<tr>");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        out.write("<td class=\"cfh\"><a href=\"?col=");
        out.write(colIdx);
        out.write("\">V</a></td>");
      }
      out.write("</tr>");
    }

    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
      out.write("<tr>");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        Player content = board.getCell(colIdx - 1, rowIdx - 1);
        char c = content.getLetter();
        String clazz = "cfe";
        if (c == 'R') {
          clazz = "cfr";
        } else if (c == 'Y') {
          clazz = "cfy";
        }
        out.write("<td class=\"");
        out.write(clazz);
        out.write("\"></td>");
      }
      out.write("</tr>");
    }
  }

}
