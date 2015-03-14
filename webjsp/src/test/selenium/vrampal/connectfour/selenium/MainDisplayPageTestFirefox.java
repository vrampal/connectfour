package vrampal.connectfour.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MainDisplayPageTestFirefox extends MainDisplayPageTest {

  private WebDriver driver;

  @Before
  public void setUp() {
    driver = new FirefoxDriver();

    String baseUrl = "http://localhost:8080/connectfour/";
    mainDisplay = new MainDisplayPageObject(driver, baseUrl);
  }

  @After
  public void tearDown() {
    driver.quit();
  }


}
