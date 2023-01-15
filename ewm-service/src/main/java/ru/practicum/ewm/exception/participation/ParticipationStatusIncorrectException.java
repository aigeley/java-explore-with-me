package ru.practicum.ewm.exception.participation;

import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;

public class ParticipationStatusIncorrectException extends RuntimeException {
    public ParticipationStatusIncorrectException(Long requestId, StatusEnum oldStatus,
                                                 StatusEnum newStatus) {
        super(String.format("%s с id = %d в статусе %s не может быть переведена в статус %s",
                ParticipationRequest.ELEMENT_NAME, requestId, oldStatus, newStatus));
    }
}
