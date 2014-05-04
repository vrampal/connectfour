package vrampal.connectfour.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import vrampal.connectfour.core.ConnectFourException;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

/**
 * Simple JUnit with Mockito.
 */
public class GameImplTest {

  // Object under test
  private GameImpl game;

  @Before
  public void setUp() throws Exception {
    game = new GameImpl();
  }

  private Player createPlayer() {
    return mock(Player.class);
  }

  @Test
  public void testBoardImpl() {
    assertEquals(GameStatus.INIT, game.getStatus());
    assertEquals(2, game.getAllPlayers().size());
    assertNotNull(game.getBoard());
    assertNull(game.getWinner());
  }

  @Test
  public void testBoardImplInt() {
    List<Player> players = new ArrayList<>();
    players.add(createPlayer());
    players.add(createPlayer());
    players.add(createPlayer());
    players.add(createPlayer());
    players.add(createPlayer());

    game = new GameImpl(players);

    assertEquals(GameStatus.INIT, game.getStatus());
    assertEquals(players, game.getAllPlayers());
    assertNotSame(players, game.getAllPlayers());
    assertNotNull(game.getBoard());
    assertNull(game.getWinner());
  }

  @Test
  public void testBeginGetCurrentPlayer() {
    Player player0 = game.getCurrentPlayer();
    assertNull(player0);

    game.begin();

    assertEquals(GameStatus.ONGOING, game.getStatus());

    Player player1 = game.getCurrentPlayer();
    assertNotNull(player1);

    game.drawGame();

    Player player2 = game.getCurrentPlayer();
    assertNull(player2);
  }

  @Test(expected = ConnectFourException.class)
  public void testBeginError() {
    game.begin();
    game.begin();
  }

  @Test(expected = ConnectFourException.class)
  public void testDropBeforeBegin() {
    game.dropDisc(3);
  }

  @Test(expected = ConnectFourException.class)
  public void testDropAfterEnd() {
    game.begin();
    game.drawGame();
    game.dropDisc(3);
  }

  @Test
  public void testDropAndPlayerAlternance() {
    game.begin();

    Player player1 = game.getCurrentPlayer();
    assertNotNull(player1);

    game.dropDisc(4);

    Player player2 = game.getCurrentPlayer();
    assertNotNull(player2);
    assertNotSame(player1, player2);
  }

  @Test
  public void testDrawGame() {
    game.begin();
    game.drawGame();

    assertEquals(GameStatus.FINISHED, game.getStatus());
    assertNull(game.getWinner());
  }

  @Test
  public void testVistory() {
    Player winner = createPlayer();

    game.begin();
    game.victory(winner);

    assertEquals(GameStatus.FINISHED, game.getStatus());
    assertSame(winner, game.getWinner());
  }

  @Test(expected = ConnectFourException.class)
  public void testDrawBeforeBegin() {
    game.drawGame();
  }

  @Test(expected = ConnectFourException.class)
  public void testVistoryBeforeBegin() {
    Player winner = createPlayer();

    game.victory(winner);
  }

}
