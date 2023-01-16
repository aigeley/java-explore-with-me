package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.event.SortEnum;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.service.EventsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
public class EventsApiController implements EventsApi {
    private final EventsService service;

    @Override
    public List<EventShortDto> getAll(String text, List<Long> categories, Boolean paid, String rangeStart,
                                      String rangeEnd, Boolean onlyAvailable, SortEnum sort, Integer from,
                                      Integer size, HttpServletRequest request) {
        return service.getAll(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size,
                request.getRemoteAddr());
    }

    @Override
    public EventFullDto get(Long id, HttpServletRequest request) {
        return service.get(id, request.getRemoteAddr());
    }
}
