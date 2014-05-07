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
import org.junit.Test;

public class BoardSerializeTest {

  private static final String CLASS_UNDER_TEST = "BoardImpl";

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

    System.out.println(CLASS_UNDER_TEST + " - Java length: " + bytes.length + " bytes");

    ByteArrayInputStream baiStream = new ByteArrayInputStream(bytes);
    ObjectInputStream oiStream = new ObjectInputStream(baiStream);

    BoardImpl board2 = (BoardImpl) oiStream.readObject();

    assertEquals(board, board2);
    assertNotSame(board, board2);

    // Old serialization duplicate the empty player making all board full.
    assertFalse(board.isFull());
    assertFalse(board2.isFull());
  }

}
