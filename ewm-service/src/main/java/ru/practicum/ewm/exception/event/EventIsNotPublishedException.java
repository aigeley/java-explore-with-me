package ru.practicum.ewm.exception.event;

import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;

public class EventIsNotPublishedException extends RuntimeException {
    public EventIsNotPublishedException(Long eventId, StateEnum state) {
        super(String.format("%s с id = %d в состоянии %s",
                Event.ELEMENT_NAME, eventId, state));
    }
}
