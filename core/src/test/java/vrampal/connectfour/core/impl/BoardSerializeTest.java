package vrampal.connectfour.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

/**
 * Simple demonstrator for Google GSON (JSON), Jackson (JSON) and Xtream (XML)
 * automatic serialization.
 */
public class BoardSerializeTest {

  private static final boolean PRINT_OUTPUT = false;

  // Object under test
  private BoardImpl board;

  @Before
  public void setUp() throws Exception {
    board = new BoardImpl();
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);

    ooStream.writeObject(board);
    byte[] bytes = baoStream.toByteArray();

    System.out.println("BoardImpl - Java length: " + bytes.length);

    ByteArrayInputStream baiStream = new ByteArrayInputStream(bytes);
    ObjectInputStream oiStream = new ObjectInputStream(baiStream);

    BoardImpl board2 = (BoardImpl) oiStream.readObject();

    assertEquals(board, board2);
    assertNotSame(board, board2);

    // Old serialization duplicate the empty player making all board full.
    assertFalse(board.isFull());
    assertFalse(board2.isFull());
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testGoogleGson() {
    Gson gsonSave = new Gson();
    String jsonStr = gsonSave.toJson(board);

    System.out.println("BoardImpl - Google GSON length: " + jsonStr.length());
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    Gson gsonRead = new Gson();
    BoardImpl board2 = gsonRead.fromJson(jsonStr, BoardImpl.class);

    assertEquals(board, board2);
    assertNotSame(board, board2);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJackson() throws IOException {
    ObjectMapper mapperSave = new ObjectMapper();
    String jsonStr = mapperSave.writeValueAsString(board);

    System.out.println("BoardImpl - Jackson length: " + jsonStr.length());
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    ObjectMapper mapperRead = new ObjectMapper();
    BoardImpl board2 = mapperRead.readValue(jsonStr, BoardImpl.class);

    assertEquals(board, board2);
    assertNotSame(board, board2);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testXstream() {
    XStream xstreamSave = new XStream();
    String xmlStr = xstreamSave.toXML(board);

    System.out.println("BoardImpl - Xstream length: " + xmlStr.length());
    if (PRINT_OUTPUT) {
      System.out.println(xmlStr);
    }

    XStream xstreamRead = new XStream();
    BoardImpl board2 = (BoardImpl) xstreamRead.fromXML(xmlStr);

    assertEquals(board, board2);
    assertNotSame(board, board2);
  }

}
