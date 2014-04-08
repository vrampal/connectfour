package vrampal.connectfour.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.impl.GameImpl;

public class ConnectFourHelper {

  private static final String GAME_KEY = "GAME";

  private Game game = null;

  private String subMessage = null;

  public void pageBegin(HttpServletRequest req) {

    HttpSession session = req.getSession();
    game = (Game) session.getAttribute(GAME_KEY);
    if (game == null) {
      game = new GameImpl();
      game.begin();
      session.setAttribute(GAME_KEY, game);
    }

    String colStr = req.getParameter("col");
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
  }

  public String getMainMessage() {
    String message = "";

    if (game.getStatus() == GameStatus.FINISHED) {
      Player winner = game.getWinner();
      if (winner != null) {
        message = winner.getName() + " won the game.";
      } else {
        message = "It's a draw game.";
      }
    } else if (game.getStatus() == GameStatus.ONGOING) {
      Player player = game.getCurrentPlayer();
      message = "Now playing: " + player.getName();
    }
    return message;
  }

  public String getSubMessage() {
    return subMessage;
  }

  public void pageEnd(HttpServletRequest req) {

    HttpSession session = req.getSession();

    if (game.getStatus() == GameStatus.FINISHED) {
      session.setAttribute(GAME_KEY, null);
    }
  }

  public Game getGame() {
    return game;
  }

}
