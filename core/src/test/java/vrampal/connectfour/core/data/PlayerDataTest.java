package vrampal.connectfour.core.data;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import vrampal.connectfour.core.SerializeTester;

public class PlayerDataTest {

  // Object under test
  private PlayerData playerData;

  @Before
  public void setUp() {
    playerData = new PlayerData("Test player", 'T');
  }

  /**
   * Not very useful but required to check we do not broke API.
   */
  @Test
  public void testToString() {
    String string = playerData.toString();

    assertTrue(string.contains("Test player"));
  }

  @Test
  public void testJavaSerialize() throws IOException, ClassNotFoundException {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testJavaSerialize(playerData, PlayerData.class);
  }

  @Test
  public void testGoogleGson() {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testGoogleGson(playerData, PlayerData.class);
  }

  @Test
  public void testJackson() throws IOException {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testJackson(playerData, PlayerData.class);
  }

  @Test
  public void testXstream() {
    SerializeTester serializeTester = new SerializeTester();
    serializeTester.testXstream(playerData, PlayerData.class);
  }

}
