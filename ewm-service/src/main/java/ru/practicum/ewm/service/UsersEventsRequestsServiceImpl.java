package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.event.EventCheck;
import ru.practicum.ewm.exception.participation.ParticipationRequestCheck;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDtoMapper;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.util.EventUtils;
import ru.practicum.ewm.service.util.ParticipationRequestUtils;
import ru.practicum.ewm.service.util.UserUtils;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class UsersEventsRequestsServiceImpl implements UsersEventsRequestsService {
    private final EventUtils eventUtils;
    private final UserUtils userUtils;
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestDtoMapper participationRequestDtoMapper;
    private final ParticipationRequestUtils participationRequestUtils;

    @Override
    public List<ParticipationRequestDto> getAll(Long userId, Long eventId) {
        User initiator = userUtils.getAndCheck(userId);
        Event event = eventUtils.getAndCheck(eventId);
        EventCheck.belongsToUser(event, initiator.getId());
        return participationRequestDtoMapper.toProjectionList(
                participationRequestRepository.findAllByEvent_IdOrderById(eventId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirm(Long userId, Long eventId, Long reqId) {
        User initiator = userUtils.getAndCheck(userId);
        Event event = eventUtils.getAndCheck(eventId);
        EventCheck.belongsToUser(event, initiator.getId());
        ParticipationRequest request = participationRequestUtils.getAndCheck(reqId);
        ParticipationRequestCheck.statusCouldBeChanged(request, StatusEnum.CONFIRMED);
        boolean isLimitCheckNeeded = event.getParticipantLimit() != 0;
        Long confirmedRequests;

        if (isLimitCheckNeeded) {
            confirmedRequests = participationRequestUtils.getConfirmedRequests(event.getId());
            ParticipationRequestCheck.participantLimitIsReached(event, confirmedRequests);
        } else {
            confirmedRequests = 0L;
        }

        request.setStatus(StatusEnum.CONFIRMED);
        ParticipationRequest requestConfirmed = participationRequestRepository.save(request);
        log.info("confirmParticipationRequest: " + requestConfirmed);

        if (isLimitCheckNeeded && ParticipationRequestCheck.isParticipantLimitReached(event, confirmedRequests + 1L)) {
            participationRequestUtils.rejectAllPending(event.getId());
        }

        return participationRequestDtoMapper.toProjection(requestConfirmed);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long eventId, Long reqId) {
        User initiator = userUtils.getAndCheck(userId);
        Event event = eventUtils.getAndCheck(eventId);
        EventCheck.belongsToUser(event, initiator.getId());
        ParticipationRequest participationRequest = participationRequestUtils.getAndCheck(reqId);
        participationRequest.setStatus(StatusEnum.REJECTED);
        ParticipationRequest participationRequestCancelled = participationRequestRepository.save(participationRequest);
        log.info("cancelParticipationRequest: " + participationRequestCancelled);
        return participationRequestDtoMapper.toProjection(participationRequestCancelled);
    }
}
