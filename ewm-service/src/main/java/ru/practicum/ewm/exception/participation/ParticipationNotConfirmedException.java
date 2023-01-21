package ru.practicum.ewm.exception.participation;

import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;

public class ParticipationNotConfirmedException extends RuntimeException {
    public ParticipationNotConfirmedException(Long userId, Long eventId) {
        super(String.format("%s с id = %d не принимал участие в %s с id = %d",
                User.ELEMENT_NAME, userId, Event.ELEMENT_NAME, eventId));
    }
}
