package ru.practicum.ewm.exception.event;

import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;

import java.time.LocalDateTime;

public class EventCheck {
    public static final int VALID_PUBLISH_HOURS = 1;

    private EventCheck() {
    }

    public static void eventDateHasGap(Event event, LocalDateTime now) {
        if (event.getEventDate().isBefore(now.plusHours(VALID_PUBLISH_HOURS))) {
            throw new EventDateTooLateException(event.getId(), event.getEventDate());
        }
    }

    public static void stateIsPending(Event event) {
        if (event.getState() != StateEnum.PENDING) {
            throw new EventStateIncorrectException(event.getId(), event.getState(), StateEnum.PUBLISHED);
        }
    }

    public static void stateIsPublished(Event event) {
        if (event.getState() != StateEnum.PUBLISHED) {
            throw new EventIsNotPublishedException(event.getId(), event.getState());
        }
    }

    public static void couldBeRejected(Event event) {
        if (event.getState() == StateEnum.PUBLISHED || event.getState() == StateEnum.CANCELED) {
            throw new EventStateIncorrectException(event.getId(), event.getState(), StateEnum.CANCELED);
        }
    }

    public static void belongsToUser(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new EventInitiatorIsDifferentException(event.getId(), userId);
        }
    }

    public static void couldBeUpdatedByUser(Event event) {
        if (event.getState() == StateEnum.PUBLISHED) {
            throw new EventStateIncorrectException(event.getId(), event.getState(), StateEnum.PENDING);
        }
    }

    public static void eventDateNotPassYet(Event event, LocalDateTime now) {
        if (event.getEventDate().isAfter(now.plusHours(VALID_PUBLISH_HOURS))) {
            throw new EventDateNotPassYetException(event.getId(), event.getEventDate());
        }
    }
}
