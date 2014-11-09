package vrampal.connectfour.webjsp;

import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import vrampal.connectfour.core.Game;

@RequiredArgsConstructor
public class ConnectFourSession {

  public static final String GAME_KEY = "game";

  private final HttpSession session;

  public Game getGame() {
    return (Game) session.getAttribute(GAME_KEY);
  }

  public void setGame(Game game) {
    session.setAttribute(GAME_KEY, game);
  }

}
