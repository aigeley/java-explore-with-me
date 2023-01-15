package ru.practicum.ewm.service.util;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;
import ru.practicum.ewm.repository.ParticipationRequestCountRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.projection.ParticipationRequestCount;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.ewm.repository.util.QParticipationRequest.participationRequest;

@Component
public class ParticipationRequestUtils extends ElementUtils<ParticipationRequest> {
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestCountRepository participationRequestCountRepository;

    public ParticipationRequestUtils(ParticipationRequestRepository participationRequestRepository,
                                     ParticipationRequestCountRepository participationRequestCountRepository) {
        super(ParticipationRequest.ELEMENT_NAME, participationRequestRepository);
        this.participationRequestRepository = participationRequestRepository;
        this.participationRequestCountRepository = participationRequestCountRepository;
    }

    public Predicate requestsAreApprovedAnd(Predicate predicate) {
        return ExpressionUtils.allOf(predicate, participationRequest.status.eq(StatusEnum.CONFIRMED));
    }

    public Long getConfirmedRequests(Long eventId) {
        Predicate wherePredicate = requestsAreApprovedAnd(
                participationRequest.event.id.eq(eventId));
        List<ParticipationRequestCount> participationRequestCountList =
                participationRequestCountRepository.getAll(wherePredicate);

        Long confirmedRequests;

        if (participationRequestCountList == null
                || participationRequestCountList.isEmpty()
                || participationRequestCountList.get(0) == null
        ) {
            confirmedRequests = 0L;
        } else {
            confirmedRequests = participationRequestCountList.get(0).getConfirmedRequests();
        }

        return confirmedRequests;
    }

    public Map<Long, Long> getConfirmedRequestsMap(List<Long> eventIds) {
        Predicate wherePredicate = requestsAreApprovedAnd(
                participationRequest.event.id.in(eventIds));
        return participationRequestCountRepository.getAll(wherePredicate)
                .stream()
                .collect(Collectors.toMap(ParticipationRequestCount::getEvent,
                        ParticipationRequestCount::getConfirmedRequests));
    }

    public void confirmAllPending(Long eventId) {
        participationRequestRepository.updateStatus(eventId, StatusEnum.PENDING, StatusEnum.CONFIRMED);
    }

    public void rejectAllPending(Long eventId) {
        participationRequestRepository.updateStatus(eventId, StatusEnum.PENDING, StatusEnum.REJECTED);
    }
}
