package vrampal.connectfour.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.impl.GameImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

/**
 * Simple demonstrator for Google GSON (JSON), Jackson (JSON) and Xtream (XML)
 * automatic serialization.
 */
public class GameDataTest {

  private static final boolean PRINT_OUTPUT = false;

  private static final String CLASS_UNDER_TEST = "GameData";

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
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);

    ooStream.writeObject(gameData);
    byte[] bytes = baoStream.toByteArray();

    System.out.println(CLASS_UNDER_TEST + " - Java length: " + bytes.length + " bytes");

    ByteArrayInputStream baiStream = new ByteArrayInputStream(bytes);
    ObjectInputStream oiStream = new ObjectInputStream(baiStream);

    GameData gameData2 = (GameData) oiStream.readObject();

    assertEquals(gameData, gameData2);
    assertNotSame(gameData, gameData2);
  }

  @Test
  public void testGoogleGson() {
    Gson gsonSave = new Gson();
    String jsonStr = gsonSave.toJson(gameData);

    System.out.println(CLASS_UNDER_TEST + " - Google GSON length: " + jsonStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    Gson gsonRead = new Gson();
    GameData gameData2 = gsonRead.fromJson(jsonStr, GameData.class);

    assertEquals(gameData, gameData2);
    assertNotSame(gameData, gameData2);
  }

  @Test
  public void testJackson() throws IOException {
    ObjectMapper mapperSave = new ObjectMapper();
    String jsonStr = mapperSave.writeValueAsString(gameData);

    System.out.println(CLASS_UNDER_TEST + " - Jackson length: " + jsonStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    ObjectMapper mapperRead = new ObjectMapper();
    GameData gameData2 = mapperRead.readValue(jsonStr, GameData.class);

    assertEquals(gameData, gameData2);
    assertNotSame(gameData, gameData2);
  }

  @Test
  public void testXstream() {
    XStream xstreamSave = new XStream();
    String xmlStr = xstreamSave.toXML(gameData);

    System.out.println(CLASS_UNDER_TEST + " - Xstream length: " + xmlStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(xmlStr);
    }

    XStream xstreamRead = new XStream();
    GameData gameData2 = (GameData) xstreamRead.fromXML(xmlStr);

    assertEquals(gameData, gameData2);
    assertNotSame(gameData, gameData2);
  }

}
