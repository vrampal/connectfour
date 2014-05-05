package vrampal.connectfour.core.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;
import vrampal.connectfour.core.data.PlayerData;

@Slf4j
@EqualsAndHashCode
@ToString(of = { "id" })
public class GameImpl implements Game, GameEndListener, Serializable {

  private static final long serialVersionUID = -266280070673809422L;

  public static final String LOG_STATS_NAME = "connectfour.game.stats";

  private static final Logger LOG_STATS = LoggerFactory.getLogger(LOG_STATS_NAME);

  private static final Player YELLOW = new PlayerData("Yellow", 'Y');

  private static final Player RED = new PlayerData("Red", 'R');

  private static final Random RAND = new Random();

  @Getter
  private final String id;

  private final List<Player> players;

  @Getter
  private final BoardImpl board;

  @Getter
  private GameStatus status = GameStatus.INIT;

  @Getter
  private int turnNumber = 0;

  @Getter
  private Player winner = null;

  public GameImpl() {
    this(generateShortId(), 2);
    players.add(YELLOW);
    players.add(RED);
  }

  public GameImpl(List<Player> playerList) {
    this(generateShortId(), playerList.size());
    players.addAll(playerList);
  }

  GameImpl(String id, int nbPlayers) {
    this.id = id;

    players = new ArrayList<>(nbPlayers);

    board = new BoardImpl();
    board.setEndGameListener(this);

    if (log.isInfoEnabled()) {
      log.info("Creating new game for " + nbPlayers + " players, id: " + id);
    }
  }

  /**
   * Main id generator, requires commons-codec, or Guava until java 8.
   */
  private static String generateShortId() {
    byte[] randData = new byte[6];
    RAND.nextBytes(randData);
    Base64 encoder = new Base64(true); // thread safe, could be static
    byte[] encodedByte = encoder.encode(randData);
    return new String(encodedByte);
  }

  /**
   * Alternative id generator.
   */
  private static String generateUUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
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

  // ----- GameEndListener methods -----

  @Override
  public void drawGame() {
    if (status != GameStatus.ONGOING) {
      String message = "drawGame operation is allowed in status: " + status;
      log.error(message);
      throw new ConnectFourException(message);
    }

    status = GameStatus.FINISHED;
    winner = null;

    if (log.isInfoEnabled()) {
      log.info("End of game id: " + id + ", draw game");
    }
  }

  @Override
  public void victory(Player winPlayer) {
    if (status != GameStatus.ONGOING) {
      String message = "victory operation is allowed in status: " + status;
      log.error(message);
      throw new ConnectFourException(message);
    }

    status = GameStatus.FINISHED;
    winner = winPlayer;

    if (log.isInfoEnabled()) {
      log.info("End of game id: " + id + ", winner: " + winner.getName());
    }
  }

}
