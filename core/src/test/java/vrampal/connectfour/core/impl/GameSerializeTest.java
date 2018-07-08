package vrampal.connectfour.core.impl;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import vrampal.connectfour.core.GenericSerializeTester;

/**
 * Simple demonstrator for Google GSON (JSON), Jackson (JSON) and Xtream (XML)
 * automatic serialization.
 */
public class GameSerializeTest {

  // Object under test
  private GameImpl game;

  @Before
  public void setUp() {
    game = new GameImpl();

    game.begin();
    game.dropDisc(3);
    game.dropDisc(2);
    game.dropDisc(5);
    game.dropDisc(2);
    game.dropDisc(3);
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJavaSerialize(game, GameImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testGoogleGson() {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testGoogleGson(game, GameImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJacksonJson() throws IOException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJacksonJson(game, GameImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJacksonXml() throws IOException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJacksonXml(game, GameImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJacksonYaml() throws IOException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJacksonYaml(game, GameImpl.class);
  }

  @Test
  public void testXstream() {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testXstream(game, GameImpl.class);
  }

}
