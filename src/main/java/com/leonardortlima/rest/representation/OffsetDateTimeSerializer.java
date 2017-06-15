package com.leonardortlima.rest.representation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeSerializer extends StdSerializer<OffsetDateTime> {

  protected OffsetDateTimeSerializer() {
    super(OffsetDateTime.class);
  }

  @Override
  public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider sp)
      throws IOException {
    gen.writeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
  }
}
