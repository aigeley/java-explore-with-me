package ru.practicum.ewm.exception.participation;

import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;

public class ParticipationForbiddenException extends RuntimeException {
    public ParticipationForbiddenException(Long userId, Long eventId) {
        super(String.format("%s с id = %d не может оставить заявку на участие в %s с id = %d",
                User.ELEMENT_NAME, userId, Event.ELEMENT_NAME, eventId));
    }
}
