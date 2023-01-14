package ru.practicum.ewm.service;

import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;

import java.util.List;

public interface UsersRequestsService {
    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto addParticipationRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
