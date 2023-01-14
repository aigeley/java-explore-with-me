package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.NewEventDto;
import ru.practicum.ewm.model.event.dto.UpdateEventRequest;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.UsersEventsService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class UsersEventsApiController extends ElementApiController<UsersEventsService> implements UsersEventsApi {
    protected UsersEventsApiController(UsersEventsService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        return service.getEvents(userId, from, size);
    }

    @Override
    public EventFullDto updateEvent_1(Long userId, UpdateEventRequest updateEventRequest) {
        return service.updateEvent_1(userId, updateEventRequest);
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        return service.addEvent(userId, newEventDto);
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        return service.getEvent(userId, eventId);
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        return service.cancelEvent(userId, eventId);
    }

    @Override
    public List<ParticipationRequestDto> getEventParticipants(Long userId, Long eventId) {
        return service.getEventParticipants(userId, eventId);
    }

    @Override
    public ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId) {
        return service.confirmParticipationRequest(userId, eventId, reqId);
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long eventId, Long reqId) {
        return service.cancelParticipationRequest(userId, eventId, reqId);
    }
}
