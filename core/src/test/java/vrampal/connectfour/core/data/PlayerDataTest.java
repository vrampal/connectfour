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

public class PlayerDataTest {

  private static final String CLASS_UNDER_TEST = "PlayerData";

  // Object under test
  private PlayerData playerData;

  @Before
  public void setUp() throws Exception {
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
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);

    ooStream.writeObject(playerData);
    byte[] bytes = baoStream.toByteArray();

    System.out.println(CLASS_UNDER_TEST + " - Java length: " + bytes.length + " bytes");

    ByteArrayInputStream baiStream = new ByteArrayInputStream(bytes);
    ObjectInputStream oiStream = new ObjectInputStream(baiStream);

    PlayerData playerData2 = (PlayerData) oiStream.readObject();

    assertEquals(playerData, playerData2);
    assertNotSame(playerData, playerData2);
  }

}
