package ru.practicum.ewm.exception.event;

import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;

public class EventStateIncorrectException extends RuntimeException {
    public EventStateIncorrectException(Long eventId, StateEnum state, StateEnum newState) {
        super(String.format("%s с id = %d в состоянии %s не может быть переведено в состояние %s",
                Event.ELEMENT_NAME, eventId, state, newState));
    }
}
