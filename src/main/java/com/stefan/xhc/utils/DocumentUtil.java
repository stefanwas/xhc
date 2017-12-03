package com.stefan.xhc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefan.xhc.model.Document;

import java.io.IOException;

public class DocumentUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Document deserialize(String json) {
        try {
            return mapper.readValue(json, Document.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String serialize(Document document) {
        try {
            return mapper.writeValueAsString(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
