package ru.practicum.ewm.service;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.exception.event.EventCheck;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventFullDtoMapper;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.EventShortDtoMapper;
import ru.practicum.ewm.model.event.dto.NewEventDto;
import ru.practicum.ewm.model.event.dto.NewEventDtoMapper;
import ru.practicum.ewm.model.event.dto.UpdateEventRequest;
import ru.practicum.ewm.model.event.dto.UpdateEventRequestMapper;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.EventWithRequestsRepository;
import ru.practicum.ewm.service.projection.EventWithRequests;
import ru.practicum.ewm.service.projection.EventWithRequestsMapper;
import ru.practicum.ewm.service.projection.EventWithViewsMapper;
import ru.practicum.ewm.service.util.CategoryUtils;
import ru.practicum.ewm.service.util.EventUtils;
import ru.practicum.ewm.service.util.ParticipationRequestUtils;
import ru.practicum.ewm.service.util.UserUtils;

import javax.transaction.Transactional;
import java.util.List;

import static ru.practicum.ewm.repository.util.QEvent.event;

@AllArgsConstructor
@Slf4j
@Service
public class UsersEventsServiceImpl implements UsersEventsService {
    private final EventRepository eventRepository;
    private final EventWithRequestsRepository eventWithRequestsRepository;
    private final EventUtils eventUtils;
    private final NewEventDtoMapper newEventDtoMapper;
    private final EventFullDtoMapper eventFullDtoMapper;
    private final EventShortDtoMapper eventShortDtoMapper;
    private final UpdateEventRequestMapper updateEventRequestMapper;
    private final EventWithViewsMapper eventWithViewsMapper;
    private final EventWithRequestsMapper eventWithRequestsMapper;
    private final UserUtils userUtils;
    private final CategoryUtils categoryUtils;
    private final ParticipationRequestUtils participationRequestUtils;

    @Override
    public List<EventShortDto> getAll(Long userId, Integer from, Integer size) {
        Predicate wherePredicate = event.initiator.id.eq(userId);
        PageRequestFromElement pageRequest = PageRequestFromElement.of(from, size, new QSort(event.id.asc()));
        List<EventWithRequests> eventsWithRequests = eventWithRequestsRepository.getAll(wherePredicate,
                null, pageRequest);
        return eventShortDtoMapper.toProjectionList(eventUtils.getEventWithViewsList(eventsWithRequests));
    }

    @Override
    @Transactional
    public EventFullDto update(Long userId, UpdateEventRequest updateEventRequest) {
        User initiator = userUtils.getAndCheck(userId);
        Event event = eventUtils.getAndCheck(updateEventRequest.getEventId());
        EventCheck.belongsToUser(event, initiator.getId());
        EventCheck.couldBeUpdatedByUser(event);
        Event eventToUpdate = updateEventRequestMapper.toElement(event, updateEventRequest);
        eventToUpdate.setState(StateEnum.PENDING);
        eventUtils.setNewCategory(eventToUpdate, event.getCategory().getId(), updateEventRequest.getCategory());
        Event eventUpdated = eventRepository.save(eventToUpdate);
        log.info("updateEvent_1: " + eventUpdated);
        return eventFullDtoMapper.toProjection(
                eventWithViewsMapper.toProjection(
                        eventWithRequestsMapper.toProjection(eventUpdated)));
    }

    @Override
    @Transactional
    public EventFullDto add(Long userId, NewEventDto newEventDto) {
        User initiator = userUtils.getAndCheck(userId);
        Category category = categoryUtils.getAndCheck(newEventDto.getCategory());
        Event event = new Event();
        event.setInitiator(initiator);
        event.setCategory(category);
        Event eventToAdd = newEventDtoMapper.toElement(event, newEventDto);
        Event eventAdded = eventRepository.save(eventToAdd);
        log.info("addEvent: " + eventAdded);
        return eventFullDtoMapper.toProjection(
                eventWithViewsMapper.toProjection(
                        eventWithRequestsMapper.toProjection(eventAdded)));
    }

    @Override
    public EventFullDto get(Long userId, Long eventId) {
        User initiator = userUtils.getAndCheck(userId);
        Event event = eventUtils.getAndCheck(eventId);
        EventCheck.belongsToUser(event, initiator.getId());
        return eventFullDtoMapper.toProjection(
                eventWithViewsMapper.toProjection(
                        eventWithRequestsMapper.toProjection(event)));
    }

    @Override
    @Transactional
    public EventFullDto cancel(Long userId, Long eventId) {
        User initiator = userUtils.getAndCheck(userId);
        Event event = eventUtils.getAndCheck(eventId);
        EventCheck.belongsToUser(event, initiator.getId());
        EventCheck.couldBeRejected(event);
        event.setState(StateEnum.CANCELED);
        Event eventCancelled = eventRepository.save(event);
        log.info("cancelEvent: " + eventCancelled);
        return eventFullDtoMapper.toProjection(
                eventWithViewsMapper.toProjection(
                        eventWithRequestsMapper.toProjection(eventCancelled)));
    }
}
