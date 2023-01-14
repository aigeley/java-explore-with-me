package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.event.SortEnum;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.service.EventsService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class EventsApiController extends ElementApiController<EventsService> implements EventsApi {
    protected EventsApiController(EventsService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<EventShortDto> getEvents_1(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, SortEnum sort, Integer from, Integer size) {
        return service.getEvents_1(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size,
                request.getRemoteAddr());
    }

    @Override
    public EventFullDto getEvent_1(Long id) {
        return service.getEvent_1(id, request.getRemoteAddr());
    }
}
