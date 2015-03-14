package vrampal.connectfour.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class MainDisplayPageTest {

  protected MainDisplayPageObject mainDisplay;

  @Test
  public void testBase() {
    mainDisplay.enterSite();
    assertEquals("ConnectFour", mainDisplay.getTitle());

    String gameId1 = mainDisplay.getGameId();
    assertTrue(gameId1.startsWith("Game id:"));

    // Test session continuity
    mainDisplay.enterSite();
    String gameId2 = mainDisplay.getGameId();
    assertTrue(gameId2.startsWith("Game id:"));
    assertEquals(gameId1, gameId2);

    // Test game reset
    mainDisplay.resetGame();
    String gameId3 = mainDisplay.getGameId();
    assertTrue(gameId3.startsWith("Game id:"));
    assertNotEquals(gameId2, gameId3);

    // Test player alternance
    assertEquals("Now playing: Yellow", mainDisplay.getMainMessage());
    assertEquals("", mainDisplay.getSubMessage());

    mainDisplay.play(4);
    assertEquals("Now playing: Red", mainDisplay.getMainMessage());
    assertEquals("", mainDisplay.getSubMessage());

    mainDisplay.play(5);
    assertEquals("Now playing: Yellow", mainDisplay.getMainMessage());
    assertEquals("", mainDisplay.getSubMessage());
  }

  @Test
  public void testErrorCases() {
    mainDisplay.play("abcxyz");
    assertEquals("Invalid parameter col: abcxyz", mainDisplay.getSubMessage());

    mainDisplay.play(95);
    assertEquals("Invalid column id: 94", mainDisplay.getSubMessage());

    mainDisplay.resetGame();

    mainDisplay.play(4);
    mainDisplay.play(4);
    mainDisplay.play(4);
    mainDisplay.play(4);
    mainDisplay.play(4);
    mainDisplay.play(4);
    mainDisplay.play(4);

    assertEquals("Column is full", mainDisplay.getSubMessage());
  }

  @Test
  public void testYellowVictory() {
    mainDisplay.resetGame();

    mainDisplay.play(4);
    mainDisplay.play(3);
    mainDisplay.play(5);
    mainDisplay.play(3);
    mainDisplay.play(3);
    mainDisplay.play(2);
    mainDisplay.play(2);
    mainDisplay.play(2);
    mainDisplay.play(2);
    mainDisplay.play(5);
    mainDisplay.play(4);

    assertEquals("Yellow won the game.", mainDisplay.getMainMessage());
    assertEquals("", mainDisplay.getSubMessage());
  }

}
