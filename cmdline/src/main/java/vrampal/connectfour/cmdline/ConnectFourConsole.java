package vrampal.connectfour.cmdline;

public class ConnectFourConsole {

  public static void main(String[] args) {
    ConsoleDiplay consoleDisplay = new ConsoleDiplay();
    PlayerInterface playerItf = new HumanPlayerInterface();
    GameRunner consoleGame = new GameRunner(playerItf, playerItf);
    consoleGame.addMonitor(consoleDisplay);
    consoleGame.run();
  }

  private ConnectFourConsole() {
    // Disabled default constructor.
  }

}
