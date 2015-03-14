package vrampal.connectfour.selenium;

import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainDisplayPageTestChrome extends MainDisplayPageTest {

  @Before
  public void setUp() {
    super.setUp(new ChromeDriver(), "http://192.168.0.130:8080/connectfour/");
  }

}
