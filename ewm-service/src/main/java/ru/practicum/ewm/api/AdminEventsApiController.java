package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.service.AdminEventsService;

import java.util.List;

@AllArgsConstructor
@RestController
public class AdminEventsApiController implements AdminEventsApi {
    private final AdminEventsService service;

    @Override
    public List<EventFullDto> getAll(List<Long> users, List<StateEnum> states, List<Long> categories,
                                     String rangeStart, String rangeEnd, Integer from, Integer size) {
        return service.getAll(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto update(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        return service.update(eventId, adminUpdateEventRequest);
    }

    @Override
    public EventFullDto publish(Long eventId) {
        return service.publish(eventId);
    }

    @Override
    public EventFullDto reject(Long eventId) {
        return service.reject(eventId);
    }
}

