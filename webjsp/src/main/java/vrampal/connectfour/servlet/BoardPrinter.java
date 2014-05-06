package vrampal.connectfour.servlet;

import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

public class BoardPrinter {

  public String printBoard(Object input) {
    if (!(input instanceof Game)) {
      return "";
    }
    Game game = (Game) input;
    return printBoard(game);
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
