package ru.practicum.ewm.exception.event;

import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.event.Event;

import java.time.LocalDateTime;

public class EventDateTooLateException extends RuntimeException {
    public EventDateTooLateException(Long eventId, LocalDateTime eventDate) {
        super(String.format("%s с id = %d с датой начала %s не может быть опубликовано",
                Event.ELEMENT_NAME, eventId, eventDate.format(ElementProjectionMapper.DATE_TIME_FORMATTER)));
    }
}
