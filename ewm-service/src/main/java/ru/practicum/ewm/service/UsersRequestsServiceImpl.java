package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        userUtils.getAndCheckElement(userId);
        return participationRequestDtoMapper.toDtoList(
                participationRequestRepository.findAllByRequester_IdOrderById(userId));
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        User requester = userUtils.getAndCheckElement(userId);
        Event event = eventUtils.getAndCheckElement(eventId);
        participationRequestUtils.checkUserCouldParticipate(event, userId);
        participationRequestUtils.checkEventIsPublished(event);
        participationRequestUtils.checkEventParticipantLimitIsZero(event);
        Long confirmedRequests = participationRequestUtils.getConfirmedRequests(event);
        participationRequestUtils.checkEventParticipantLimit(event, confirmedRequests);
        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setRequester(requester);
        participationRequest.setEvent(event);

        if (!event.getRequestModeration()) {
            participationRequest.setStatus(StatusEnum.CONFIRMED);
        }

        ParticipationRequest participationRequestAdded = participationRequestRepository.save(participationRequest);
        log.info("addParticipationRequest: " + participationRequestAdded);
        return participationRequestDtoMapper.toDto(participationRequestAdded);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userUtils.getAndCheckElement(userId);
        ParticipationRequest participationRequest = participationRequestUtils.getAndCheckElement(requestId);
        participationRequestUtils.checkRequestBelongsToUser(participationRequest, userId);
        participationRequest.setStatus(StatusEnum.CANCELED);
        ParticipationRequest participationRequestCancelled = participationRequestRepository.save(participationRequest);
        log.info("cancelRequest: " + participationRequestCancelled);
        return participationRequestDtoMapper.toDto(participationRequestCancelled);
    }
}
