package vrampal.connectfour.core.impl;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import vrampal.connectfour.core.SerializeTester;

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
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testJavaSerialize(game, GameImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testGoogleGson() {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testGoogleGson(game, GameImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJackson() throws IOException {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testJackson(game, GameImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testXstream() {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testXstream(game, GameImpl.class);
  }

}
