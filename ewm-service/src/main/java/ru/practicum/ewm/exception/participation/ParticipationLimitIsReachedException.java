package ru.practicum.ewm.exception.participation;

import ru.practicum.ewm.model.event.Event;

public class ParticipationLimitIsReachedException extends RuntimeException {
    public ParticipationLimitIsReachedException(Long eventId, Integer participantLimit) {
        super(String.format("%s с id = %d достигло максимального числа заявок на участие %d",
                Event.ELEMENT_NAME, eventId, participantLimit));
    }
}
