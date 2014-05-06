package vrampal.connectfour.core;

import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Not very useful but required to check we do not broke API.
 */
public class ConnectFourExceptionTest {

  @Test
  public void testConnectFourExceptionString() {
    String message = "This is a test message";

    ConnectFourException cfe = new ConnectFourException(message);

    assertSame(message,  cfe.getMessage());
  }

  @Test
  public void testConnectFourExceptionStringThrowable() {
    String message = "This is a test message";
    Throwable cause = new NullPointerException();

    ConnectFourException cfe = new ConnectFourException(message, cause);

    assertSame(message,  cfe.getMessage());
    assertSame(cause, cfe.getCause());
  }

}
