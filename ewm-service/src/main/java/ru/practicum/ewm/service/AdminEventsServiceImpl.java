package ru.practicum.ewm.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.exception.event.EventCheck;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequestMapper;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventFullDtoMapper;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.EventWithRequestsRepository;
import ru.practicum.ewm.service.projection.EventWithRequests;
import ru.practicum.ewm.service.projection.EventWithRequestsMapper;
import ru.practicum.ewm.service.projection.EventWithViewsMapper;
import ru.practicum.ewm.service.util.EventUtils;
import ru.practicum.ewm.service.util.ParticipationRequestUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.element.model.ElementProjectionMapper.DATE_TIME_FORMATTER;
import static ru.practicum.ewm.repository.util.QEvent.event;

@AllArgsConstructor
@Slf4j
@Service
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventRepository eventRepository;
    private final EventWithRequestsRepository eventWithRequestsRepository;
    private final EventUtils eventUtils;
    private final EventFullDtoMapper eventFullDtoMapper;
    private final AdminUpdateEventRequestMapper adminUpdateEventRequestMapper;
    private final EventWithRequestsMapper eventWithRequestsMapper;
    private final EventWithViewsMapper eventWithViewsMapper;
    private final ParticipationRequestUtils participationRequestUtils;

    @Override
    public List<EventFullDto> getAll(List<Long> users, List<StateEnum> states, List<Long> categories,
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

        List<EventWithRequests> eventWithRequestsList =
                eventWithRequestsRepository.getAll(wherePredicate, null, pageRequest);

        return eventFullDtoMapper.toProjectionList(
                eventWithViewsMapper.toProjectionList(eventWithRequestsList));
    }

    @Override
    @Transactional
    public EventFullDto update(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventUtils.getAndCheck(eventId);
        Event eventToUpdate = adminUpdateEventRequestMapper.toElement(event, adminUpdateEventRequest);
        eventUtils.setNewCategory(eventToUpdate, event.getCategory().getId(), adminUpdateEventRequest.getCategory());

        if (eventToUpdate.getState() == StateEnum.PUBLISHED &&
                eventToUpdate.getRequestModeration().equals(event.getRequestModeration()) &&
                !eventToUpdate.getRequestModeration()) {
            //одобряем все ожидающие запросы, если у опубликованного события сняли ограничение на модерацию
            participationRequestUtils.confirmAllPending(eventToUpdate.getId());
        }

        Event eventUpdated = eventRepository.save(eventToUpdate);
        log.info("updateEvent: " + eventUpdated);
        return eventFullDtoMapper.toProjection(
                eventWithViewsMapper.toProjection(
                        eventWithRequestsMapper.toProjection(eventUpdated)));
    }

    @Override
    @Transactional
    public EventFullDto publish(Long eventId) {
        Event event = eventUtils.getAndCheck(eventId);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        EventCheck.eventDateHasGap(event, now);
        EventCheck.stateIsPending(event);
        event.setState(StateEnum.PUBLISHED);
        event.setPublishedOn(now);
        Event eventPublished = eventRepository.save(event);
        log.info("publishEvent: " + eventPublished);
        return eventFullDtoMapper.toProjection(
                eventWithViewsMapper.toProjection(
                        eventWithRequestsMapper.toProjection(eventPublished)));
    }

    @Override
    @Transactional
    public EventFullDto reject(Long eventId) {
        Event event = eventUtils.getAndCheck(eventId);
        EventCheck.couldBeRejected(event);
        event.setState(StateEnum.CANCELED);
        Event eventRejected = eventRepository.save(event);
        log.info("rejectEvent: " + eventRejected);
        return eventFullDtoMapper.toProjection(
                eventWithViewsMapper.toProjection(
                        eventWithRequestsMapper.toProjection(eventRejected)));
    }
}
