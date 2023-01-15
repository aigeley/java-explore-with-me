package ru.practicum.ewm.service;

import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.model.event.dto.EventFullDto;

import java.util.List;

public interface AdminEventsService {
    List<EventFullDto> getAll(List<Long> users, List<StateEnum> states, List<Long> categories, String rangeStart,
                              String rangeEnd, Integer from, Integer size);

    EventFullDto update(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publish(Long eventId);

    EventFullDto reject(Long eventId);
}
