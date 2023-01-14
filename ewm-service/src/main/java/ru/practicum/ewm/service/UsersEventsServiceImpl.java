package ru.practicum.ewm.service;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
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
import ru.practicum.ewm.model.event.projection.EventWithRequests;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDtoMapper;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.EventRepositoryCustom;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.util.CategoryUtils;
import ru.practicum.ewm.service.util.EventUtils;
import ru.practicum.ewm.service.util.ParticipationRequestUtils;
import ru.practicum.ewm.service.util.UserUtils;

import java.util.List;

import static ru.practicum.ewm.repository.util.QEvent.event;

@AllArgsConstructor
@Slf4j
@Service
public class UsersEventsServiceImpl implements UsersEventsService {
    private final EventRepository eventRepository;
    private final EventRepositoryCustom eventRepositoryCustom;
    private final EventUtils eventUtils;
    private final NewEventDtoMapper newEventDtoMapper;
    private final EventFullDtoMapper eventFullDtoMapper;
    private final EventShortDtoMapper eventShortDtoMapper;
    private final UpdateEventRequestMapper updateEventRequestMapper;
    private final UserUtils userUtils;
    private final CategoryUtils categoryUtils;
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestDtoMapper participationRequestDtoMapper;
    private final ParticipationRequestUtils participationRequestUtils;

    @Override
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        Predicate wherePredicate = event.initiator.id.eq(userId);
        PageRequestFromElement pageRequest = PageRequestFromElement.of(from, size, new QSort(event.id.asc()));
        List<EventWithRequests> eventsWithRequests = eventRepositoryCustom.getEventWithRequestsList(wherePredicate,
                null, pageRequest);
        return eventShortDtoMapper.toDtoList(eventUtils.getEventWithViewsList(eventsWithRequests));
    }

    @Override
    public EventFullDto updateEvent_1(Long userId, UpdateEventRequest updateEventRequest) {
        User initiator = userUtils.getAndCheckElement(userId);
        Event event = eventUtils.getAndCheckElement(updateEventRequest.getEventId());
        eventUtils.checkEventBelongsToUser(event, initiator.getId());
        eventUtils.checkEventCouldBeUpdatedByUser(event);
        Event eventToUpdate = updateEventRequestMapper.toElement(event, updateEventRequest);
        eventToUpdate.setState(StateEnum.PENDING);
        Event eventUpdated = eventRepository.save(eventToUpdate);
        log.info("updateEvent_1: " + eventUpdated);
        return eventFullDtoMapper.toDto(eventUtils.toEventWithViews(eventUpdated));
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User initiator = userUtils.getAndCheckElement(userId);
        Category category = categoryUtils.getAndCheckElement(newEventDto.getCategory());
        Event event = new Event();
        event.setInitiator(initiator);
        event.setCategory(category);
        Event eventToAdd = newEventDtoMapper.toElement(event, newEventDto);
        Event eventAdded = eventRepository.save(eventToAdd);
        log.info("addEvent: " + eventAdded);
        return eventFullDtoMapper.toDto(eventUtils.toEventWithViews(eventAdded));
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        User initiator = userUtils.getAndCheckElement(userId);
        Event event = eventUtils.getAndCheckElement(eventId);
        eventUtils.checkEventBelongsToUser(event, initiator.getId());
        return eventFullDtoMapper.toDto(eventUtils.toEventWithViews(event));
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        User initiator = userUtils.getAndCheckElement(userId);
        Event event = eventUtils.getAndCheckElement(eventId);
        eventUtils.checkEventBelongsToUser(event, initiator.getId());
        eventUtils.checkEventCouldBeRejected(event);
        event.setState(StateEnum.CANCELED);
        Event eventCancelled = eventRepository.save(event);
        log.info("cancelEvent: " + eventCancelled);
        return eventFullDtoMapper.toDto(eventUtils.toEventWithViews(eventCancelled));
    }

    @Override
    public List<ParticipationRequestDto> getEventParticipants(Long userId, Long eventId) {
        User initiator = userUtils.getAndCheckElement(userId);
        Event event = eventUtils.getAndCheckElement(eventId);
        eventUtils.checkEventBelongsToUser(event, initiator.getId());
        return participationRequestDtoMapper.toDtoList(
                participationRequestRepository.findAllByEvent_IdOrderById(eventId));
    }

    @Override
    public ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId) {
        User initiator = userUtils.getAndCheckElement(userId);
        Event event = eventUtils.getAndCheckElement(eventId);
        eventUtils.checkEventBelongsToUser(event, initiator.getId());
        participationRequestUtils.checkEventParticipantLimitIsZero(event);
        Long confirmedRequests = participationRequestUtils.getConfirmedRequests(event);
        participationRequestUtils.checkEventParticipantLimit(event, confirmedRequests);
        ParticipationRequest participationRequest = participationRequestUtils.getAndCheckElement(reqId);
        participationRequest.setStatus(StatusEnum.CONFIRMED);
        ParticipationRequest participationRequestConfirmed = participationRequestRepository.save(participationRequest);
        log.info("confirmParticipationRequest: " + participationRequestConfirmed);
        return participationRequestDtoMapper.toDto(participationRequestConfirmed);
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long eventId, Long reqId) {
        User initiator = userUtils.getAndCheckElement(userId);
        Event event = eventUtils.getAndCheckElement(eventId);
        eventUtils.checkEventBelongsToUser(event, initiator.getId());
        ParticipationRequest participationRequest = participationRequestUtils.getAndCheckElement(reqId);
        participationRequest.setStatus(StatusEnum.REJECTED);
        ParticipationRequest participationRequestCancelled = participationRequestRepository.save(participationRequest);
        log.info("cancelParticipationRequest: " + participationRequestCancelled);
        return participationRequestDtoMapper.toDto(participationRequestCancelled);
    }
}
