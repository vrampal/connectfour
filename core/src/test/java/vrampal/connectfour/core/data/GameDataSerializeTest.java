package vrampal.connectfour.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

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

public class GameDataSerializeTest {

  private static final boolean PRINT_OUTPUT = false;

  private GameData gameData;

  @Before
  public void setUp() throws Exception {
    Game game = new GameImpl();

    game.begin();
    game.dropDisc(3);
    game.dropDisc(2);
    game.dropDisc(5);
    game.dropDisc(2);
    game.dropDisc(3);

    gameData = new GameData(game);
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);

    ooStream.writeObject(gameData);
    byte[] bytes = baoStream.toByteArray();

    System.out.println("GameData - Java length: " + bytes.length);

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

    System.out.println("GameData - Google GSON length: " + jsonStr.length());
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

    System.out.println("GameData - Jackson length: " + jsonStr.length());
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

    System.out.println("GameData - Xstream length: " + xmlStr.length());
    if (PRINT_OUTPUT) {
      System.out.println(xmlStr);
    }

    XStream xstreamRead = new XStream();
    GameData gameData2 = (GameData) xstreamRead.fromXML(xmlStr);

    assertEquals(gameData, gameData2);
    assertNotSame(gameData, gameData2);
  }

}
