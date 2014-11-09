package vrampal.connectfour.webjsp.view;

import javax.servlet.http.HttpSession;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.webjsp.ConnectFourSession;

/**
 * Used by main-display.jsp to print the board.
 */
public class BoardPrinter {

  public String printBoard(HttpSession ses) {
    ConnectFourSession session = new ConnectFourSession(ses);
    Game game = session.getGame();
    String retVal = "";
    if (game != null) {
      retVal = printBoard(game);
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
        if (board.isColumnFull(colIdx - 1)) {
          printEmptyHeader(buff);
        } else {
          printPlayHeader(buff, colIdx);
        }
      }
      buff.append("</tr>");
    }

    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
      buff.append("<tr>");
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        Player cellContent = board.getCell(colIdx - 1, rowIdx - 1);
        printCell(buff, cellContent);
      }
      buff.append("</tr>");
    }

    return buff.toString();
  }

  void printEmptyHeader(StringBuilder buff) {
    buff.append("<td class=\"cfh\">&nbsp;</td>");
  }

  void printPlayHeader(StringBuilder buff, int colIdx) {
    buff.append("<td class=\"cfh\"><a href=\"?col=");
    buff.append(colIdx);
    buff.append("\">V</a></td>");
  }

  void printCell(StringBuilder buff, Player content) {
    String clazz = selectClassFromPlayer(content);
    buff.append("<td class=\"");
    buff.append(clazz);
    buff.append("\"></td>");
  }

  String selectClassFromPlayer(Player player) {
    String clazz;
    switch (player.getLetter()) {
    case 'R':
      clazz = "cfr";
      break;
    case 'Y':
      clazz = "cfy";
      break;
    default:
      clazz = "cfe";
      break;
    }
    return clazz;
  }

}
