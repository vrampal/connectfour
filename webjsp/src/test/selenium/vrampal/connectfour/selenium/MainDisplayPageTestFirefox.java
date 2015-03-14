package vrampal.connectfour.selenium;

import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MainDisplayPageTestFirefox extends MainDisplayPageTest {

  @Before
  public void setUp() {
    super.setUp(new FirefoxDriver(), "http://192.168.0.130:8080/connectfour/");
  }

}
