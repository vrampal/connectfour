package vrampal.connectfour.core;

/**
 * Unchecked exception for ConnectFour. Message is mandatory.
 */
public class ConnectFourException extends RuntimeException {

  public ConnectFourException(String message) {
    super(message);
  }

  public ConnectFourException(String message, Throwable cause) {
    super(message, cause);
  }

}
