package vrampal.connectfour.core.impl;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

public class GameImpl implements Game, GameEndListener, Serializable {

  @Getter
  private final String id;

  @Getter
  private final BoardImpl board;

  private final List<Player> players;

  @Getter
  private GameStatus status = GameStatus.INIT;

  private int turnNumber = 0;

  @Getter
  private Player winner = null;

  public GameImpl(String id) {
    this.id = id;
    board = new BoardImpl(this);
    players = new ArrayList<>(2);
    createDefaultPlayers();
  }

  public GameImpl(String id, List<Player> players) {
    this.id = id;
    board = new BoardImpl(this);
    this.players = new ArrayList<>(players.size());
    this.players.addAll(players);
  }

  private void createDefaultPlayers() {
    Player yellow = new PlayerImpl("Yellow", 'Y', Color.YELLOW);
    players.add(yellow);
    Player red = new PlayerImpl("Red", 'R', Color.RED);
    players.add(red);
  }

  @Override
  public List<Player> getAllPlayers() {
    return Collections.unmodifiableList(players);
  }

  @Override
  public void start() {
    if (status != GameStatus.INIT) {
      throw new ConnectFourException("Invalid status: " + status);
    }
    status = GameStatus.ONGOING;
  }

  @Override
  public Player getCurrentPlayer() {
    if (status != GameStatus.ONGOING) {
      throw new ConnectFourException("Invalid status: " + status);
    }
    int currentPlayerIndex = turnNumber % players.size();
    return players.get(currentPlayerIndex);
  }

  @Override
  public void dropDisc(int colIdx) {
    if (status != GameStatus.ONGOING) {
      throw new ConnectFourException("Invalid status: " + status);
    }
    board.dropDisc(getCurrentPlayer(), colIdx);
    turnNumber++;
  }

  @Override
  public void drawGame() {
    status = GameStatus.FINISHED;
    winner = null;
  }

  @Override
  public void victory(Player player) {
    status = GameStatus.FINISHED;
    winner = player;
  }

}
