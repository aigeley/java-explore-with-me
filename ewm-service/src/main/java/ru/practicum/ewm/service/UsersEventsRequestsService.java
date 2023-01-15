package ru.practicum.ewm.service;

import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;

import java.util.List;

public interface UsersEventsRequestsService {
    List<ParticipationRequestDto> getAll(Long userId, Long eventId);

    ParticipationRequestDto confirm(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto cancel(Long userId, Long eventId, Long reqId);
}
