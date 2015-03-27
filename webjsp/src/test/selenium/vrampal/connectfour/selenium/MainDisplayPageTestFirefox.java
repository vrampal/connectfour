package vrampal.connectfour.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MainDisplayPageTestFirefox extends MainDisplayPageTest {

  private WebDriver driver;

  @Before
  public void setUp() {
    driver = new FirefoxDriver();
    driver.manage().window().setPosition(new Point(0,0));
    driver.manage().window().setSize(new Dimension(1024,768));

    String baseUrl = "http://localhost:8080/connectfour/";
    mainDisplay = new MainDisplayPageObject(driver, baseUrl);
  }

  @After
  public void tearDown() {
    driver.quit();
  }


}
