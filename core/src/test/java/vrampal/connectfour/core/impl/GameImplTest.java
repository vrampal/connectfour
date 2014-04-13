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

public class GameImplTest {

  private GameImpl game;

  @Before
  public void setUp() throws Exception {
    game = new GameImpl();
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
  }

  @Test(expected = ConnectFourException.class)
  public void testBeginError() {
    game.begin();
    game.begin();
  }

  @Test
  public void testDropGetCurrentPlayer() {
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
    game.drawGame();

    assertEquals(GameStatus.FINISHED, game.getStatus());
    assertNull(game.getWinner());
  }

  @Test
  public void testVistory() {
    Player winner = createPlayer();

    game.victory(winner);

    assertEquals(GameStatus.FINISHED, game.getStatus());
    assertSame(winner, game.getWinner());
  }

  private Player createPlayer() {
    return mock(Player.class);
  }

}
