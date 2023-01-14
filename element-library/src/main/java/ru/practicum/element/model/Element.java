package ru.practicum.element.model;

import org.apache.commons.lang3.NotImplementedException;

import java.time.format.DateTimeFormatter;

public abstract class Element {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public abstract Long getId();

    public void setId(Long id) {
        throw new NotImplementedException();
    }
}
