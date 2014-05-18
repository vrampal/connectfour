package vrampal.connectfour.core;

import java.util.List;

public interface GameFactory {

  Game createGame();

  Game createGame(List<Player> playerList);

}
