package ru.practicum.ewm.exception.participation;

import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.user.User;

public class RequestorIsDifferentException extends RuntimeException {
    public RequestorIsDifferentException(Long requestId, Long userId) {
        super(String.format("%s с id = %d не принадлежит %s с id = %d",
                ParticipationRequest.ELEMENT_NAME, requestId, User.ELEMENT_NAME, userId));
    }
}
