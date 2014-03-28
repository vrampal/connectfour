package vrampal.connectfour.core.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

public class GameImpl implements Game, GameEndListener {

  @Getter
  private final String id;

  @Getter
  private final BoardImpl board;

  @Getter
  private final List<Player> players;

  @Getter
  private GameStatus status = GameStatus.INIT;

  private int currentPlayerIndex = 0;

  @Getter
  private Player winner = null;

  public GameImpl() {
    this("");
  }

  public GameImpl(String id) {
    this.id = id;
    board = new BoardImpl(this);
    players = new ArrayList<>(2);
    players.add(new PlayerImpl("Player 1", 'Y', Color.YELLOW));
    players.add(new PlayerImpl("Player 2", 'R', Color.RED));
  }

  @Override
  public void draw() {
    status = GameStatus.FINISHED;
  }

  @Override
  public void victory(Player player) {
    status = GameStatus.FINISHED;
    winner = player;
  }

  @Override
  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  @Override
  public void dropDisc(int colIdx) {
    board.dropDisc(getCurrentPlayer(), colIdx);
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

}
