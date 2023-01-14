package ru.practicum.ewm.service;

import ru.practicum.ewm.model.event.SortEnum;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;

import java.util.List;

public interface EventsService {
    List<EventShortDto> getEvents_1(String text, List<Long> categories, Boolean paid, String rangeStart,
                                    String rangeEnd, Boolean onlyAvailable, SortEnum sort, Integer from, Integer size,
                                    String ip);

    EventFullDto getEvent_1(Long id, String ip);
}
