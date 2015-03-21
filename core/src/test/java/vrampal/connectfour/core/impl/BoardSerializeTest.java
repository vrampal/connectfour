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
public class BoardSerializeTest {

  // Object under test
  private BoardImpl board;

  @Before
  public void setUp() {
    board = new BoardImpl();
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJavaSerialize(board, BoardImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testGoogleGson() {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testGoogleGson(board, BoardImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJackson() throws IOException {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testJackson(board, BoardImpl.class);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testXstream() {
    GenericSerializeTester serializeTester = new GenericSerializeTester();
    serializeTester.testXstream(board, BoardImpl.class);
  }

}
