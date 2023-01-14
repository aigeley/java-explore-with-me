package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.service.AdminEventsService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class AdminEventsApiController extends ElementApiController<AdminEventsService> implements AdminEventsApi {
    protected AdminEventsApiController(AdminEventsService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<EventFullDto> getEvents_2(List<Long> users, List<StateEnum> states, List<Long> categories,
                                          String rangeStart, String rangeEnd, Integer from, Integer size) {
        return service.getEvents_2(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        return service.updateEvent(eventId, adminUpdateEventRequest);
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        return service.publishEvent(eventId);
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        return service.rejectEvent(eventId);
    }
}

