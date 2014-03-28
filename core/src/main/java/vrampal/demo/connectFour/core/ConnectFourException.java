package vrampal.demo.connectFour.core;

class ConnectFourException extends RuntimeException {

  private static final long serialVersionUID = -7258111692186320847L;

  ConnectFourException(String message) {
    super(message);
  }

  ConnectFourException(String message, Throwable cause) {
    super(message, cause);
  }

}
