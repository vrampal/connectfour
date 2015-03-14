package vrampal.connectfour.selenium;

import org.junit.Before;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class MainDisplayPageTestIE extends MainDisplayPageTest {

  @Before
  public void setUp() {
    super.setUp(new InternetExplorerDriver(), "http://192.168.0.130:8080/connectfour/");
  }

}
