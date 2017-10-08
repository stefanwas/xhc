package com.stefan.xhc.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static String format(LocalDateTime dateTime) {
        return DATE_TIME_FORMATTER.format(dateTime);
    }

    public static LocalDateTime parse(String text) {
        return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
    }
}
