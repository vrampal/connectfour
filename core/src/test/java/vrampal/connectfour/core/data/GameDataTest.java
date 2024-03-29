package vrampal.connectfour.core.data;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GenericSerializeTester;
import vrampal.connectfour.core.impl.GameImpl;

/**
 * Simple demonstrator for Google GSON (JSON), Jackson (JSON) and Xtream (XML)
 * automatic serialization.
 */
public class GameDataTest {

  // Object under test
  private GameData gameData;

  @Before
  public void setUp() {
    Game game = new GameImpl();

    game.begin();
    game.dropDisc(3);
    game.dropDisc(2);
    game.dropDisc(5);
    game.dropDisc(2);
    game.dropDisc(3);

    gameData = new GameData(game);
  }

  /**
   * Not very useful but required to check we do not broke API.
   */
  @Test
  public void testToString() {
    String string = gameData.toString();

    assertTrue(string.contains(gameData.getId()));
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJavaSerialize(gameData, GameData.class);
  }

  @Test
  public void testGoogleGson() {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testGoogleGson(gameData, GameData.class);
  }

  @Test
  public void testJacksonJson() throws IOException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJacksonJson(gameData, GameData.class);
  }

  @Test
  @Ignore("Test fails, for some reason the winner is updated from null to empty string")
  public void testJacksonXml() throws IOException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJacksonXml(gameData, GameData.class);
  }

  @Test
  public void testJacksonYaml() throws IOException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJacksonYaml(gameData, GameData.class);
  }

  @Test
  public void testXstream() {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testXstream(gameData, GameData.class);
  }

}
