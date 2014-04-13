package vrampal.connectfour.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTesting {

  private WebDriver driver;

  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    // driver = new InternetExplorerDriver();
    driver = new FirefoxDriver();
    // driver = new ChromeDriver();

    baseUrl = "http://localhost:8080/connect-four-jsp/";

    driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
  }

  @Test
  public void testBase() throws Exception {
    driver.get(baseUrl);
    assertEquals("ConnectFour", driver.getTitle());

    String gameId1 = getGameId();
    assertTrue(gameId1.startsWith("Game id:"));

    // Test session continuity
    driver.get(baseUrl);
    String gameId2 = getGameId();
    assertTrue(gameId2.startsWith("Game id:"));
    assertEquals(gameId1, gameId2);

    // Test game reset
    driver.get(baseUrl + "?reset=1");
    String gameId3 = getGameId();
    assertTrue(gameId3.startsWith("Game id:"));
    assertNotEquals(gameId2, gameId3);

    // Test player alternance
    assertEquals("Now playing: Yellow", getMainMessage());
    assertEquals("", getSubMessage());

    play(4);
    assertEquals("Now playing: Red", getMainMessage());
    assertEquals("", getSubMessage());

    play(5);
    assertEquals("Now playing: Yellow", getMainMessage());
    assertEquals("", getSubMessage());
  }

  @Test
  public void testErrorCases() throws Exception {
    driver.get(baseUrl + "?col=abcxyz");
    assertEquals("Invalid parameter col: abcxyz", getSubMessage());

    driver.get(baseUrl + "?col=95");
    assertEquals("Invalid column id: 94", getSubMessage());

    driver.get(baseUrl + "?reset=1");
    play(4);
    play(4);
    play(4);
    play(4);
    play(4);
    play(4);
    play(4);
    assertEquals("Column is full", getSubMessage());
  }

  @Test
  public void testYellowVictory() throws Exception {
    driver.get(baseUrl + "?reset=1");
    play(4);
    play(3);
    play(5);
    play(3);
    play(3);
    play(2);
    play(2);
    play(2);
    play(2);
    play(5);
    play(4);
    assertEquals("Yellow won the game.", getMainMessage());
    assertEquals("", getSubMessage());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  private String getMainMessage() {
    return driver.findElement(By.cssSelector("h2")).getText();
  }

  private String getSubMessage() {
    return driver.findElement(By.cssSelector("h3")).getText();
  }

  private String getGameId() {
    return driver.findElement(By.cssSelector("p")).getText();
  }

  private void play(int colIdx) {
    driver.findElement(By.xpath("(//a[contains(text(),'V')])[" + colIdx + "]")).click();
  }

}
