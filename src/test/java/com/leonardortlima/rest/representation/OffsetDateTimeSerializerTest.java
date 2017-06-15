package com.leonardortlima.rest.representation;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author leonardortlima
 * @since 2017-06-13
 */
public class OffsetDateTimeSerializerTest {

  @Test
  public void serializeOffsetDateTime() throws IOException {
    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    new OffsetDateTimeSerializer().serialize(
        OffsetDateTime.of(2017, 6, 13, 17, 44, 22, 123_456_789, ZoneOffset.ofHours(-3))
        , jsonGenerator, serializerProvider);
    jsonGenerator.flush();

    assertEquals("\"2017-06-13T17:44:22.123456789-03:00\"", jsonWriter.toString());
  }
}