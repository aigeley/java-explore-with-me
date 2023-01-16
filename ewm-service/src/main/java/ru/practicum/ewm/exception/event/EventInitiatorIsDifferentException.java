package ru.practicum.ewm.exception.event;

import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;

public class EventInitiatorIsDifferentException extends RuntimeException {
    public EventInitiatorIsDifferentException(Long eventId, Long userId) {
        super(String.format("%s с id = %d не принадлежит %s с id = %d",
                Event.ELEMENT_NAME, eventId, User.ELEMENT_NAME, userId));
    }
}
