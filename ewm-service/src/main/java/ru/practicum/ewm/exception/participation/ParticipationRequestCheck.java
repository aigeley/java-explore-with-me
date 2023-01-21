package ru.practicum.ewm.exception.participation;

import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;

public class ParticipationRequestCheck {
    private ParticipationRequestCheck() {
    }

    public static void userCouldParticipate(Event event, Long userId) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new ParticipationForbiddenException(userId, event.getId());
        }
    }

    public static boolean isParticipantLimitReached(Event event, Long confirmedRequests) {
        return event.getParticipantLimit() != 0L && confirmedRequests.equals(event.getParticipantLimit().longValue());
    }

    public static void participantLimitIsReached(Event event, Long confirmedRequests) {
        if (isParticipantLimitReached(event, confirmedRequests)) {
            throw new ParticipationLimitIsReachedException(event.getId(), event.getParticipantLimit());
        }
    }

    public static void requestBelongsToUser(ParticipationRequest participationRequest, Long userId) {
        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new ParticipationRequestorIsDifferentException(participationRequest.getId(), userId);
        }
    }

    public static void statusCouldBeChanged(ParticipationRequest request, StatusEnum newStatus) {
        if (request.getStatus() == newStatus) {
            throw new ParticipationStatusIncorrectException(request.getId(), request.getStatus(), newStatus);
        }
    }

    public static void userHasBeenOnEvent(ParticipationRequest request, Long userId, Long eventId) {
        if (request == null) {
            throw new ParticipationNotConfirmedException(userId, eventId);
        }
    }
}
