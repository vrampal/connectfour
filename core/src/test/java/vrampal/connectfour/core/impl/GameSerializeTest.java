package vrampal.connectfour.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import vrampal.connectfour.core.Game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class GameSerializeTest {

  private Game game;

  @Before
  public void setUp() throws Exception {
    game = new GameImpl();
    game.begin();
    game.dropDisc(3);
    game.dropDisc(2);
    game.dropDisc(5);
    game.dropDisc(2);
    game.dropDisc(3);
  }

  @Test
  @Ignore("Not yet implemented")
  public void testJavaSerialize() {
    fail("Not yet implemented");
  }

  @Test
  @Ignore("Not yet stable")
  public void testGoogleGson() {
    Gson gsonSave = new Gson();
    String jsonStr = gsonSave.toJson(game);

    System.out.println(jsonStr);

    Gson gsonRead = new Gson();
    Game game2 = gsonRead.fromJson(jsonStr, GameImpl.class);

    assertEquals(game, game2);
    assertNotSame(game, game2);
  }

  @Test
  @Ignore("Not yet stable")
  public void testJackson() throws IOException {
    ObjectMapper mapperSave = new ObjectMapper();
    String jsonStr = mapperSave.writeValueAsString(game);

    System.out.println(jsonStr);

    ObjectMapper mapperRead = new ObjectMapper();
    Game game2 = mapperRead.readValue(jsonStr, GameImpl.class);

    assertEquals(game, game2);
    assertNotSame(game, game2);
  }

  @Test
  @Ignore("Not yet stable")
  public void testXstream() {
    XStream xstreamSave = new XStream();
    String xmlStr = xstreamSave.toXML(game);

    System.out.println(xmlStr);

    XStream xstreamRead = new XStream();
    Game game2 = (GameImpl) xstreamRead.fromXML(xmlStr);

    assertEquals(game, game2);
    assertNotSame(game, game2);
  }

}
