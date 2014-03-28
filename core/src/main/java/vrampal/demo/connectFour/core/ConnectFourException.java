package vrampal.demo.connectFour.core;

public class ConnectFourException extends RuntimeException {

  private static final long serialVersionUID = -7258111692186320847L;

  public ConnectFourException(String message) {
    super(message);
  }

  public ConnectFourException(String message, Throwable cause) {
    super(message, cause);
  }

}
