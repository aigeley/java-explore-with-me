package ru.practicum.ewm.service;

import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.model.event.dto.EventFullDto;

import java.util.List;

public interface AdminEventsService {
    List<EventFullDto> getEvents_2(List<Long> users, List<StateEnum> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}
