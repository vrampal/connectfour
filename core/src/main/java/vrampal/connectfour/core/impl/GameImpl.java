package vrampal.connectfour.core.impl;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

  private GameImpl(int nbPlayers) {
    id = UUID.randomUUID().toString();
    board = new BoardImpl(this);
    players = new ArrayList<>(nbPlayers);
  }

  public GameImpl() {
    this(2);
    Player yellow = new DefaultPlayerImpl("Yellow", 'Y', Color.YELLOW);
    players.add(yellow);
    Player red = new DefaultPlayerImpl("Red", 'R', Color.RED);
    players.add(red);
  }

  public GameImpl(List<Player> players) {
    this(players.size());
    this.players.addAll(players);
  }

  @Override
  public List<Player> getAllPlayers() {
    return Collections.unmodifiableList(players);
  }

  @Override
  public void begin() {
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
