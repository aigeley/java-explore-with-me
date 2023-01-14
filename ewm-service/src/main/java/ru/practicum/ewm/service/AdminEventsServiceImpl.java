package ru.practicum.ewm.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequestMapper;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventFullDtoMapper;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.util.EventUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;
import static ru.practicum.ewm.repository.util.QEvent.event;

@AllArgsConstructor
@Slf4j
@Service
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventRepository eventRepository;
    private final EventUtils eventUtils;
    private final EventFullDtoMapper eventFullDtoMapper;
    private final AdminUpdateEventRequestMapper adminUpdateEventRequestMapper;

    @Override
    public List<EventFullDto> getEvents_2(List<Long> users, List<StateEnum> states, List<Long> categories,
                                          String rangeStart, String rangeEnd, Integer from, Integer size) {
        List<Predicate> wherePredicates = new ArrayList<>();

        if (users != null && !users.isEmpty()) {
            wherePredicates.add(event.initiator.id.in(users));
        }

        if (states != null && !states.isEmpty()) {
            wherePredicates.add(event.state.in(states));
        }

        if (categories != null && !categories.isEmpty()) {
            wherePredicates.add(event.category.id.in(categories));
        }

        if (rangeStart != null) {
            wherePredicates.add(event.eventDate.goe(LocalDateTime.parse(rangeStart, DATE_TIME_FORMATTER)));
        } else {
            //по умолчанию, выгружать будущие события
            wherePredicates.add(event.eventDate.goe(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));
        }

        if (rangeEnd != null) {
            wherePredicates.add(event.eventDate.loe(LocalDateTime.parse(rangeEnd, DATE_TIME_FORMATTER)));
        }

        Predicate wherePredicate = ExpressionUtils.allOf(wherePredicates);
        PageRequestFromElement pageRequest = PageRequestFromElement.of(from, size, new QSort(event.id.asc()));
        return eventFullDtoMapper.toDtoList(
                eventUtils.getEventWithViewsListByPredicate(wherePredicate, null, pageRequest));
    }

    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventUtils.getAndCheckElement(eventId);
        Event eventToUpdate = adminUpdateEventRequestMapper.toElement(event, adminUpdateEventRequest);
        Event eventUpdated = eventRepository.save(eventToUpdate);
        log.info("updateEvent: " + eventUpdated);
        return eventFullDtoMapper.toDto(eventUtils.toEventWithViews(eventUpdated));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = eventUtils.getAndCheckElement(eventId);
        eventUtils.checkEventCouldBePublished(event);
        event.setState(StateEnum.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        Event eventPublished = eventRepository.save(event);
        log.info("publishEvent: " + eventPublished);
        return eventFullDtoMapper.toDto(eventUtils.toEventWithViews(eventPublished));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventUtils.getAndCheckElement(eventId);
        eventUtils.checkEventCouldBeRejected(event);
        event.setState(StateEnum.CANCELED);
        Event eventRejected = eventRepository.save(event);
        log.info("rejectEvent: " + eventRejected);
        return eventFullDtoMapper.toDto(eventUtils.toEventWithViews(eventRejected));
    }
}
