package com.stefan.xhc.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.stefan.xhc.utils.DateTimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class DateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateTimeText = jsonParser.getText() != null ? jsonParser.getText() : null;
        return DateTimeUtil.parse(dateTimeText);
    }
}
