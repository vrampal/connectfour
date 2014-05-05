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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class PlayerDataTest {

  private static final boolean PRINT_OUTPUT = false;

  // Object under test
  private PlayerData playerData;

  @Before
  public void setUp() throws Exception {
    playerData = new PlayerData("Test player", 'T');
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);

    ooStream.writeObject(playerData);
    byte[] bytes = baoStream.toByteArray();

    System.out.println("PlayerData - Java length: " + bytes.length + " bytes");

    ByteArrayInputStream baiStream = new ByteArrayInputStream(bytes);
    ObjectInputStream oiStream = new ObjectInputStream(baiStream);

    PlayerData playerData2 = (PlayerData) oiStream.readObject();

    assertEquals(playerData, playerData2);
    assertNotSame(playerData, playerData2);
  }

  @Test
  public void testGoogleGson() {
    Gson gsonSave = new Gson();
    String jsonStr = gsonSave.toJson(playerData);

    System.out.println("PlayerData - Google GSON length: " + jsonStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    Gson gsonRead = new Gson();
    PlayerData playerData2 = gsonRead.fromJson(jsonStr, PlayerData.class);

    assertEquals(playerData, playerData2);
    assertNotSame(playerData, playerData2);
  }

  @Test
  public void testJackson() throws IOException {
    ObjectMapper mapperSave = new ObjectMapper();
    String jsonStr = mapperSave.writeValueAsString(playerData);

    System.out.println("PlayerData - Jackson length: " + jsonStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(jsonStr);
    }

    ObjectMapper mapperRead = new ObjectMapper();
    PlayerData playerData2 = mapperRead.readValue(jsonStr, PlayerData.class);

    assertEquals(playerData, playerData2);
    assertNotSame(playerData, playerData2);
  }

  @Test
  public void testXstream() {
    XStream xstreamSave = new XStream();
    String xmlStr = xstreamSave.toXML(playerData);

    System.out.println("PlayerData - Xstream length: " + xmlStr.length() + " chars");
    if (PRINT_OUTPUT) {
      System.out.println(xmlStr);
    }

    XStream xstreamRead = new XStream();
    PlayerData playerData2 = (PlayerData) xstreamRead.fromXML(xmlStr);

    assertEquals(playerData, playerData2);
    assertNotSame(playerData, playerData2);
  }

}