package vrampal.connectfour.selenium;

import lombok.RequiredArgsConstructor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@RequiredArgsConstructor
public class MainDisplayPageObject {

  private final WebDriver driver;

  private final String baseUrl;

  public void enterSite() {
    driver.get(baseUrl);
  }

  public void resetGame() {
    driver.get(baseUrl + "?reset=1");
  }

  public void play(int colIdx) {
    play(Integer.toString(colIdx));
  }

  public void play(String colStr) {
    driver.get(baseUrl + "?col=" + colStr);
  }

  public String getTitle() {
    return driver.getTitle();
  }

  public String getMainMessage() {
    return driver.findElement(By.cssSelector("h1")).getText();
  }

  public String getSubMessage() {
    return driver.findElement(By.cssSelector("h2")).getText();
  }

  public String getGameId() {
    return driver.findElement(By.cssSelector("p")).getText();
  }

}
