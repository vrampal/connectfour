package vrampal.connectfour.cmdline.robot;

import vrampal.connectfour.cmdline.ConsoleDiplay;
import vrampal.connectfour.cmdline.GameRunner;
import vrampal.connectfour.cmdline.PlayerInterface;

public class ConnectFourRobot {

  public static void main(String[] args) {
    ConsoleDiplay consoleDisplay = new ConsoleDiplay();
    PlayerInterface playerItf = new DummyBotPlayerInterface();
    GameRunner consoleGame = new GameRunner(playerItf, playerItf);
    consoleGame.addMonitor(consoleDisplay);
    consoleGame.run();
  }

}
