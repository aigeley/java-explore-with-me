package ru.practicum.ewm.service.util;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.exception.participation.EventIsNotPublishedException;
import ru.practicum.ewm.exception.participation.ParticipationLimitIsReachedException;
import ru.practicum.ewm.exception.participation.RequestorIsDifferentException;
import ru.practicum.ewm.exception.participation.UserCouldNotParticipateException;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;
import ru.practicum.ewm.model.participation.projection.ParticipationRequestCount;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepositoryCustom;

import java.util.List;

import static ru.practicum.ewm.repository.util.QParticipationRequest.participationRequest;

@Component
public class ParticipationRequestUtils extends ElementUtils<ParticipationRequest> {
    private final ParticipationRequestRepositoryCustom participationRequestRepositoryCustom;

    public ParticipationRequestUtils(ParticipationRequestRepository participationRequestRepository,
                                     ParticipationRequestRepositoryCustom participationRequestRepositoryCustom) {
        super(ParticipationRequest.ELEMENT_NAME, participationRequestRepository);
        this.participationRequestRepositoryCustom = participationRequestRepositoryCustom;
    }

    public Predicate requestsAreApprovedAnd(Predicate predicate) {
        return ExpressionUtils.allOf(predicate, participationRequest.status.eq(StatusEnum.CONFIRMED));
    }

    public void checkUserCouldParticipate(Event event, Long userId) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new UserCouldNotParticipateException(userId, event.getId());
        }
    }

    public void checkEventIsPublished(Event event) {
        if (event.getState() != StateEnum.PUBLISHED) {
            throw new EventIsNotPublishedException(event.getId(), event.getState());
        }
    }

    public Long getConfirmedRequests(Event event) {
        Predicate wherePredicate = requestsAreApprovedAnd(
                participationRequest.event.id.eq(event.getId()));
        List<ParticipationRequestCount> participationRequestCountList =
                participationRequestRepositoryCustom.getParticipationRequestsCountList(wherePredicate);
        Long confirmedRequests;

        if (participationRequestCountList != null
                && !participationRequestCountList.isEmpty()
                && participationRequestCountList.get(0) != null
        ) {
            confirmedRequests = participationRequestCountList.get(0).getConfirmedRequests();
        } else {
            confirmedRequests = 0L;
        }

        return confirmedRequests;
    }

    public void checkEventParticipantLimit(Event event, Long confirmedRequests) {
        Integer participantLimit = event.getParticipantLimit();

        if (confirmedRequests.equals(participantLimit.longValue())) {
            throw new ParticipationLimitIsReachedException(event.getId(), event.getParticipantLimit());
        }
    }

    public void checkEventParticipantLimitIsZero(Event event) {
        Integer participantLimit = event.getParticipantLimit();

        if (participantLimit == 0) {
            throw new ParticipationLimitIsReachedException(event.getId(), event.getParticipantLimit());
        }
    }

    public void checkRequestBelongsToUser(ParticipationRequest participationRequest, Long userId) {
        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new RequestorIsDifferentException(participationRequest.getId(), userId);
        }
    }
}
