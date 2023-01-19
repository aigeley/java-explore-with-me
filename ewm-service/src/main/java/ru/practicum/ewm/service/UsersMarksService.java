package ru.practicum.ewm.service;

import ru.practicum.ewm.model.mark.dto.MarkDto;

public interface UsersMarksService {
    MarkDto add(Long userId, Long eventId, Boolean markValue);

    MarkDto get(Long userId, Long eventId);
}
