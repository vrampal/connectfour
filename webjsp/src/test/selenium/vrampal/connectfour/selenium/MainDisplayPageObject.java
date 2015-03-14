package vrampal.connectfour.selenium;

import lombok.RequiredArgsConstructor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@RequiredArgsConstructor
public class MainDisplayPageObject {

  private final WebDriver driver;

  public String getMainMessage() {
    return driver.findElement(By.cssSelector("h1")).getText();
  }

  public String getSubMessage() {
    return driver.findElement(By.cssSelector("h2")).getText();
  }

  public String getGameId() {
    return driver.findElement(By.cssSelector("p")).getText();
  }

  public void play(int colIdx) {
    driver.findElement(By.xpath("(//a[contains(text(),'V')])[" + colIdx + "]")).click();
  }

}
