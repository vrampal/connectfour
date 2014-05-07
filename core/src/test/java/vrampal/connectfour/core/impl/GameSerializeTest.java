package vrampal.connectfour.core.impl;

import static org.junit.Assert.assertEquals;
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
public class GameSerializeTest {

  private static final boolean PRINT_OUTPUT = false;

  private static final String CLASS_UNDER_TEST = "GameImpl";

  // Object under test
  private GameImpl game;

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
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);

    ooStream.writeObject(game);
    byte[] bytes = baoStream.toByteArray();

    System.out.println(CLASS_UNDER_TEST + " - Java length: " + bytes.length + " bytes");

    ByteArrayInputStream baiStream = new ByteArrayInputStream(bytes);
    ObjectInputStream oiStream = new ObjectInputStream(baiStream);

    GameImpl game2 = (GameImpl) oiStream.readObject();

    assertEquals(game, game2);
    assertNotSame(game, game2);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testGoogleGson() {
    Gson gsonSave = new Gson();
    String jsonStr = gsonSave.toJson(game);

    System.out.println(CLASS_UNDER_TEST + " - Google GSON length: " + jsonStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    Gson gsonRead = new Gson();
    GameImpl game2 = gsonRead.fromJson(jsonStr, GameImpl.class);

    assertEquals(game, game2);
    assertNotSame(game, game2);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testJackson() throws IOException {
    ObjectMapper mapperSave = new ObjectMapper();
    String jsonStr = mapperSave.writeValueAsString(game);

    System.out.println(CLASS_UNDER_TEST + " - Jackson length: " + jsonStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    ObjectMapper mapperRead = new ObjectMapper();
    GameImpl game2 = mapperRead.readValue(jsonStr, GameImpl.class);

    assertEquals(game, game2);
    assertNotSame(game, game2);
  }

  @Test
  @Ignore("This is an object model with circular references, auto serialize does not work")
  public void testXstream() {
    XStream xstreamSave = new XStream();
    String xmlStr = xstreamSave.toXML(game);

    System.out.println(CLASS_UNDER_TEST + " - Xstream length: " + xmlStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(xmlStr);
    }

    XStream xstreamRead = new XStream();
    GameImpl game2 = (GameImpl) xstreamRead.fromXML(xmlStr);

    assertEquals(game, game2);
    assertNotSame(game, game2);
  }

}
