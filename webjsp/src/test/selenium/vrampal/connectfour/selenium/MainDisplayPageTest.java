package vrampal.connectfour.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class MainDisplayPageTest {

  private WebDriver driver;

  private String baseUrl;

  private MainDisplayPageObject mainDisplay;

  public void setUp(WebDriver driver, String baseUrl) {
    this.driver = driver;
    this.baseUrl = baseUrl;

    mainDisplay = new MainDisplayPageObject(driver);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testBase() {
    driver.get(baseUrl);
    assertEquals("ConnectFour", driver.getTitle());

    String gameId1 = mainDisplay.getGameId();
    assertTrue(gameId1.startsWith("Game id:"));

    // Test session continuity
    driver.get(baseUrl);
    String gameId2 = mainDisplay.getGameId();
    assertTrue(gameId2.startsWith("Game id:"));
    assertEquals(gameId1, gameId2);

    // Test game reset
    driver.get(baseUrl + "?reset=1");
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
    MainDisplayPageObject mainDisplay = new MainDisplayPageObject(driver);

    driver.get(baseUrl + "?col=abcxyz");
    assertEquals("Invalid parameter col: abcxyz", mainDisplay.getSubMessage());

    driver.get(baseUrl + "?col=95");
    assertEquals("Invalid column id: 94", mainDisplay.getSubMessage());

    driver.get(baseUrl + "?reset=1");

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
    MainDisplayPageObject mainDisplay = new MainDisplayPageObject(driver);

    driver.get(baseUrl + "?reset=1");

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
