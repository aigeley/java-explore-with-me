package ru.practicum.ewm.service;

import ru.practicum.ewm.model.event.dto.EventShortDto;

import java.util.List;

public interface UsersRecommendationsService {
    List<EventShortDto> getAll(Long userId);
}
