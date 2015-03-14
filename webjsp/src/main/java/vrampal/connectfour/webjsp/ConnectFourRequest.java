package vrampal.connectfour.webjsp;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConnectFourRequest {

  /**
   * Type: String
   */
  public static final String MAIN_MESSAGE = "main_message";

  /**
   * Type: String
   */
  public static final String SUB_MESSAGE = "sub_message";

  /**
   * Type: String
   */
  public static final String GAME_ID = "game_id";

  private final HttpServletRequest req;

  // ----- Session -----

  public ConnectFourSession getSession() {
    return new ConnectFourSession(req.getSession());
  }

  // ----- Request parameters -----

  public String getParameter(String name) {
    return req.getParameter(name);
  }

  // ----- Request attributes -----

  public String getMainMessage() {
    return (String) req.getAttribute(MAIN_MESSAGE);
  }

  public void setMainMessage(String mainMessage) {
    req.setAttribute(MAIN_MESSAGE, mainMessage);
  }

  public String getSubMessage() {
    return (String) req.getAttribute(SUB_MESSAGE);
  }

  public void setSubMessage(String subMessage) {
    req.setAttribute(SUB_MESSAGE, subMessage);
  }

  public String getGameId() {
    return (String) req.getAttribute(GAME_ID);
  }

  public void setGameId(String gameId) {
    req.setAttribute(GAME_ID, gameId);
  }

}
