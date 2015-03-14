package vrampal.connectfour.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class MainDisplayPageTestIE extends MainDisplayPageTest {

  private WebDriver driver;

  @Before
  public void setUp() {
    driver = new InternetExplorerDriver();

    String baseUrl = "http://localhost:8080/connectfour/";
    mainDisplay = new MainDisplayPageObject(driver, baseUrl);
  }

  @After
  public void tearDown() {
    driver.quit();
  }


}
