package vrampal.connectfour.robot;

import vrampal.connectfour.cmdline.PlayerInterface;
import vrampal.connectfour.core.Game;

public class LearningBotPlayerInterface implements PlayerInterface {

  private static final GamesData ROOT = new GamesData();

  private GamesData curGame = ROOT;

  @Override
  public int selectPlayColumn(Game game) {
    // TODO Auto-generated method stub
    return 0;
  }

}
