package vrampal.connectfour.core.impl;

import java.util.List;

import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameFactory;
import vrampal.connectfour.core.Player;

public class GameFactoryImpl implements GameFactory {

  private static final GameFactory INSTANCE = new GameFactoryImpl();

  public static GameFactory getInstance() {
    return INSTANCE;
  }

  private GameFactoryImpl() {
    // Disabled default constructor.
  }

  @Override
  public Game createGame() {
    return new GameImpl();
  }

  @Override
  public Game createGame(List<Player> playerList) {
    return new GameImpl(playerList);
  }

}
