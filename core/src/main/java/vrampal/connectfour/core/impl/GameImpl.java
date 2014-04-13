package vrampal.connectfour.core.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

@Slf4j
public class GameImpl implements Game, GameEndListener, Serializable {

  public static final String LOG_STATS_NAME = "connectfour.game.stats";

  private static final Logger LOG_STATS = LoggerFactory.getLogger(LOG_STATS_NAME);

  private static final Player YELLOW = new DefaultPlayerImpl("Yellow", 'Y');

  private static final Player RED = new DefaultPlayerImpl("Red", 'R');

  private static final Random RAND = new Random();

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

  public GameImpl() {
    this(2);
    players.add(YELLOW);
    players.add(RED);
  }

  public GameImpl(List<Player> players) {
    this(players.size());
    this.players.addAll(players);
  }

  private GameImpl(int nbPlayers) {
    id = generateShortId();
    board = new BoardImpl(this);
    players = new ArrayList<>(nbPlayers);
    if (log.isInfoEnabled()) {
      log.info("Creating new game for " + nbPlayers + " players, id: " + id);
    }
  }

  private String generateUUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  private String generateShortId() {
    byte[] randData = new byte[6];
    RAND.nextBytes(randData);
    return Base64.encodeBase64URLSafeString(randData);
  }

  @Override
  public List<Player> getAllPlayers() {
    return Collections.unmodifiableList(players);
  }

  @Override
  public void begin() {
    if (status != GameStatus.INIT) {
      String message = "begin operation is allowed in status: " + status;
      log.error(message);
      throw new ConnectFourException(message);
    }
    status = GameStatus.ONGOING;
    if (log.isInfoEnabled()) {
      log.info("Begin of game id: " + id);
    }
  }

  @Override
  public Player getCurrentPlayer() {
    Player currentPlayer = null;
    if (status == GameStatus.ONGOING) {
      int currentPlayerIndex = turnNumber % players.size();
      currentPlayer = players.get(currentPlayerIndex);
    }
    return currentPlayer;
  }

  @Override
  public void dropDisc(int colIdx) {
    if (status != GameStatus.ONGOING) {
      String message = "dropDisc operation is allowed in status: " + status;
      log.error(message);
      throw new ConnectFourException(message);
    }
    Player currentPlayer = getCurrentPlayer();
    board.dropDisc(currentPlayer, colIdx);
    turnNumber++;
    if (LOG_STATS.isInfoEnabled()) {
      LOG_STATS.info(id + ',' + currentPlayer.getName() + ',' + colIdx + ',' + status);
    }
  }

  @Override
  public void drawGame() {
    status = GameStatus.FINISHED;
    winner = null;
    if (log.isInfoEnabled()) {
      log.info("End of game id: " + id + ", draw game");
    }
  }

  @Override
  public void victory(Player player) {
    status = GameStatus.FINISHED;
    winner = player;
    if (log.isInfoEnabled()) {
      log.info("End of game id: " + id + ", winner: " + winner.getName());
    }
  }

}
