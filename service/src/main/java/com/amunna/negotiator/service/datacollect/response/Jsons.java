package com.amunna.negotiator.service.datacollect.response;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import java.io.IOException;

public class Jsons {

    private Jsons() {}

    private static final ObjectMapper JSON = new MappingJsonFactory().getCodec();

    public static String toJson(Object value) {
        try {
            return JSON.writeValueAsString(value);
        } catch (IOException e) {
            // Shouldn't get I/O errors writing to a string.
            throw Throwables.propagate(e);
        }
    }

    public static <T> T fromJson(String string, Class<T> valueType) {
        try {
            return JSON.readValue(string, valueType);
        } catch (IOException e) {
            // Must be malformed JSON.  Other kinds of I/O errors don't get thrown when reading from a string.
            throw new IllegalArgumentException(e.toString());
        }
    }
}
