package ru.practicum.ewm.service;

import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.NewEventDto;
import ru.practicum.ewm.model.event.dto.UpdateEventRequest;

import java.util.List;

public interface UsersEventsService {
    List<EventShortDto> getAll(Long userId, Integer from, Integer size);

    EventFullDto update(Long userId, UpdateEventRequest updateEventRequest);

    EventFullDto add(Long userId, NewEventDto newEventDto);

    EventFullDto get(Long userId, Long eventId);

    EventFullDto cancel(Long userId, Long eventId);
}
