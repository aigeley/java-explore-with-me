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
public class UsersRequestsServiceImpl implements UsersRequestsService {
    private final EventUtils eventUtils;
    private final UserUtils userUtils;
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestDtoMapper participationRequestDtoMapper;
    private final ParticipationRequestUtils participationRequestUtils;

    @Override
    public List<ParticipationRequestDto> getAll(Long userId) {
        userUtils.getAndCheck(userId);
        return participationRequestDtoMapper.toProjectionList(
                participationRequestRepository.findAllByRequester_IdOrderById(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto add(Long userId, Long eventId) {
        User requester = userUtils.getAndCheck(userId);
        Event event = eventUtils.getAndCheck(eventId);
        ParticipationRequestCheck.userCouldParticipate(event, userId);
        EventCheck.stateIsPublished(event);
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(requester);
        request.setEvent(event);
        boolean isLimitCheckNeeded = !(event.getRequestModeration() || event.getParticipantLimit() == 0);
        Long confirmedRequests;

        if (isLimitCheckNeeded) {
            confirmedRequests = participationRequestUtils.getConfirmedRequests(event.getId());
            ParticipationRequestCheck.participantLimitIsReached(event, confirmedRequests);
            request.setStatus(StatusEnum.CONFIRMED);
        } else {
            confirmedRequests = 0L;
        }

        ParticipationRequest requestAdded = participationRequestRepository.save(request);
        log.info("addParticipationRequest: " + requestAdded);

        if (isLimitCheckNeeded && ParticipationRequestCheck.isParticipantLimitReached(event, confirmedRequests + 1L)) {
            participationRequestUtils.rejectAllPending(event.getId());
        }

        return participationRequestDtoMapper.toProjection(requestAdded);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        userUtils.getAndCheck(userId);
        ParticipationRequest participationRequest = participationRequestUtils.getAndCheck(requestId);
        ParticipationRequestCheck.requestBelongsToUser(participationRequest, userId);
        participationRequest.setStatus(StatusEnum.CANCELED);
        ParticipationRequest participationRequestCancelled = participationRequestRepository.save(participationRequest);
        log.info("cancelRequest: " + participationRequestCancelled);
        return participationRequestDtoMapper.toProjection(participationRequestCancelled);
    }
}
