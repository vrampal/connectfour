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
public class BoardSerializeTest {

  // Object under test
  private BoardImpl board;

  @Before
  public void setUp() {
    board = new BoardImpl();
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testJavaSerialize(board, BoardImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testGoogleGson() {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testGoogleGson(board, BoardImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJackson() throws IOException {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testJackson(board, BoardImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testXstream() {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testXstream(board, BoardImpl.class);
  }

}
