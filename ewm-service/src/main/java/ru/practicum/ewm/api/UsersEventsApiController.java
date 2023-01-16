package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.NewEventDto;
import ru.practicum.ewm.model.event.dto.UpdateEventRequest;
import ru.practicum.ewm.service.UsersEventsService;

import java.util.List;

@AllArgsConstructor
@RestController
public class UsersEventsApiController implements UsersEventsApi {
    private final UsersEventsService service;

    @Override
    public List<EventShortDto> getAll(Long userId, Integer from, Integer size) {
        return service.getAll(userId, from, size);
    }

    @Override
    public EventFullDto update(Long userId, UpdateEventRequest updateEventRequest) {
        return service.update(userId, updateEventRequest);
    }

    @Override
    public EventFullDto add(Long userId, NewEventDto newEventDto) {
        return service.add(userId, newEventDto);
    }

    @Override
    public EventFullDto get(Long userId, Long eventId) {
        return service.get(userId, eventId);
    }

    @Override
    public EventFullDto cancel(Long userId, Long eventId) {
        return service.cancel(userId, eventId);
    }
}
