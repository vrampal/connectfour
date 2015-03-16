package vrampal.connectfour.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

@Slf4j
public class SerializeTester {

  private final boolean printOutput;

  public SerializeTester() {
    this(false);
  }

  public SerializeTester(boolean printOutput) {
    this.printOutput = printOutput;
  }

  public <T> void testJavaSerialize(T testObj, Class<T> testClass) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);

    // Control we can serialize
    ooStream.writeObject(testObj);
    byte[] bytes = baoStream.toByteArray();

    log.info(testClass.getSimpleName() + " - Java length: " + bytes.length + " bytes");

    ByteArrayInputStream baiStream = new ByteArrayInputStream(bytes);
    ObjectInputStream oiStream = new ObjectInputStream(baiStream);

    // Control we can de-serialize
    T testObj2 = (T) oiStream.readObject();

    // Control same object fields
    assertEquals(testObj, testObj2);
    assertNotSame(testObj, testObj2);
  }

  public <T> void testGoogleGson(T testObj, Class<T> testClass) {
    Gson gson = new Gson();

    // Control we can serialize
    String jsonStr = gson.toJson(testObj);

    log.info(testClass.getSimpleName() + " - Google GSON length: " + jsonStr.length() + " chars");
    if (printOutput) {
      log.info(jsonStr);
    }

    // Control we can de-serialize
    T testObj2 = gson.fromJson(jsonStr, testClass);

    // Control same object fields
    assertEquals(testObj, testObj2);
    assertNotSame(testObj, testObj2);

    // Serialize again
    String jsonStr2 = gson.toJson(testObj2);

    // Control no data lost in serialization process
    assertEquals(jsonStr, jsonStr2);
  }

  public <T> void testJackson(T testObj, Class<T> testClass) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    // Control we can serialize
    String jsonStr = objectMapper.writeValueAsString(testObj);

    log.info(testClass.getSimpleName() + " - Jackson length: " + jsonStr.length() + " chars");
    if (printOutput) {
      log.info(jsonStr);
    }

    // Control we can de-serialize
    T testObj2 = objectMapper.readValue(jsonStr, testClass);

    // Control same object fields
    assertEquals(testObj, testObj2);
    assertNotSame(testObj, testObj2);

    // Serialize again
    String jsonStr2 = objectMapper.writeValueAsString(testObj);

    // Control no data lost in serialization process
    assertEquals(jsonStr, jsonStr2);
  }

  public <T> void testXstream(T testObj, Class<T> testClass) {
    XStream xstream = new XStream();

    // Control we can serialize
    String xmlStr = xstream.toXML(testObj);

    log.info(testClass.getSimpleName() + " - Xstream length: " + xmlStr.length() + " chars");
    if (printOutput) {
      log.info(xmlStr);
    }

    // Control we can de-serialize
    T testObj2 = (T) xstream.fromXML(xmlStr);

    // Control same object fields
    assertEquals(testObj, testObj2);
    assertNotSame(testObj, testObj2);

    // Serialize again
    String xmlStr2 = xstream.toXML(testObj);

    // Control no data lost in serialization process
    assertEquals(xmlStr, xmlStr2);
  }

}
