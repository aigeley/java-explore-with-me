package ru.practicum.ewm.service;

import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.NewEventDto;
import ru.practicum.ewm.model.event.dto.UpdateEventRequest;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;

import java.util.List;

public interface UsersEventsService {
    List<EventShortDto> getEvents(Long userId, Integer from, Integer size);

    EventFullDto updateEvent_1(Long userId, UpdateEventRequest updateEventRequest);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEvent(Long userId, Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> getEventParticipants(Long userId, Long eventId);

    ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long eventId, Long reqId);
}
