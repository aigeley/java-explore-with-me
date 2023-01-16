package ru.practicum.ewm.service;

import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;

import java.util.List;

public interface UsersRequestsService {
    List<ParticipationRequestDto> getAll(Long userId);

    ParticipationRequestDto add(Long userId, Long eventId);

    ParticipationRequestDto cancel(Long userId, Long requestId);
}
