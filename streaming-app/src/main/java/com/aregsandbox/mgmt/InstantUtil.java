package com.aregsandbox.mgmt;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class InstantUtil {
    public static DateTimeFormatter TZ_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSx").withZone(ZoneOffset.UTC);
    public static DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_INSTANT;

    public static class Serializer extends JsonSerializer<Instant> {
        private final DateTimeFormatter fmt;

        public Serializer(DateTimeFormatter fmt) {
            this.fmt = fmt;
        }
    
        @Override
        public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeString("");
            } else {
                String strTimestamp = fmt.format(value);
                gen.writeString(strTimestamp);
            }
        }
    }

    public static class Deserializer extends JsonDeserializer<Instant> {
        private final DateTimeFormatter fmt;
        
        public Deserializer(DateTimeFormatter fmt) {
            this.fmt = fmt;
        }

        @Override
        public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String text = p.getText();              
            if(text == null || "".equals(text))
                return null;
            else 
                return Instant.from(fmt.parse(p.getText()));
        }
    }
}
