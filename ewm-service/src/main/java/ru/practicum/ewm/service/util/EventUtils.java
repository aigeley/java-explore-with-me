package ru.practicum.ewm.service.util;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.EwmApplication;
import ru.practicum.ewm.exception.event.EventStateIncorrectException;
import ru.practicum.ewm.exception.event.InitiatorIsDifferentException;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.projection.EventWithRequests;
import ru.practicum.ewm.model.event.projection.EventWithViews;
import ru.practicum.ewm.model.participation.projection.ParticipationRequestCount;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.EventRepositoryCustom;
import ru.practicum.ewm.repository.ParticipationRequestRepositoryCustom;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;
import static ru.practicum.ewm.repository.util.QParticipationRequest.participationRequest;

@Component
public class EventUtils extends ElementUtils<Event> {
    private static final int STATS_MONTHS = 1;
    private final EventRepositoryCustom eventRepositoryCustom;
    private final ParticipationRequestRepositoryCustom participationRequestRepositoryCustom;
    private final ParticipationRequestUtils participationRequestUtils;
    private final EwmStatsClient ewmStatsClient;

    public EventUtils(EventRepository eventRepository, EventRepositoryCustom eventRepositoryCustom,
                      ParticipationRequestRepositoryCustom participationRequestRepositoryCustom,
                      ParticipationRequestUtils participationRequestUtils, EwmStatsClient ewmStatsClient) {
        super(Event.ELEMENT_NAME, eventRepository);
        this.eventRepositoryCustom = eventRepositoryCustom;
        this.participationRequestRepositoryCustom = participationRequestRepositoryCustom;
        this.participationRequestUtils = participationRequestUtils;
        this.ewmStatsClient = ewmStatsClient;
    }

    public void hit(String uri, String ip) {
        EndpointHit endpointHit = new EndpointHit(
                null,
                EwmApplication.APPLICATION_NAME,
                uri,
                ip,
                LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).format(DATE_TIME_FORMATTER)
        );
        ewmStatsClient.hit(endpointHit);
    }

    public void checkEventCouldBePublished(Event event) {
        if (event.getState() != StateEnum.PENDING) {
            throw new EventStateIncorrectException(event.getId(), event.getState(), StateEnum.PUBLISHED);
        }
    }

    public void checkEventCouldBeRejected(Event event) {
        if (event.getState() == StateEnum.PUBLISHED) {
            throw new EventStateIncorrectException(event.getId(), event.getState(), StateEnum.CANCELED);
        }
    }

    public void checkEventBelongsToUser(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new InitiatorIsDifferentException(event.getId(), userId);
        }
    }

    public void checkEventCouldBeUpdatedByUser(Event event) {
        if (event.getState() == StateEnum.PUBLISHED) {
            throw new EventStateIncorrectException(event.getId(), event.getState(), StateEnum.PENDING);
        }
    }

    public List<EventWithViews> getEventWithViewsList(List<EventWithRequests> eventsWithRequests) {
        Map<String, EventWithRequests> urisEvents = eventsWithRequests.stream()
                .collect(Collectors.toMap(event -> "/events/" + event.getId(), event -> event));

        List<ViewStats> viewStats = ewmStatsClient.getStats(
                LocalDateTime.now()
                        .truncatedTo(ChronoUnit.MICROS)
                        .minusMonths(STATS_MONTHS)
                        .format(DATE_TIME_FORMATTER),
                LocalDateTime.now()
                        .truncatedTo(ChronoUnit.MICROS)
                        .format(DATE_TIME_FORMATTER),
                urisEvents.keySet(),
                false
        );

        Map<String, Long> urisViews = viewStats.stream()
                .collect(Collectors.toMap(ViewStats::getUri, ViewStats::getHits));

        List<EventWithViews> eventsWithViews = new ArrayList<>();

        for (String uri : urisEvents.keySet()) {
            EventWithRequests eventWithRequests = urisEvents.get(uri);
            Long confirmedRequests = eventWithRequests.getConfirmedRequests();
            Long views = urisViews.get(uri);
            eventsWithViews.add(new EventWithViews(
                    eventWithRequests.getEvent(),
                    confirmedRequests != null ? confirmedRequests : 0L,
                    views != null ? views : 0L
            ));
        }

        return eventsWithViews;
    }

    public EventWithViews toEventWithViews(Event event) {
        Long confirmedRequests = participationRequestUtils.getConfirmedRequests(event);
        EventWithRequests eventWithRequests = new EventWithRequests(event, confirmedRequests);
        return getEventWithViewsList(Arrays.asList(eventWithRequests))
                .get(0);
    }

    public Collection<EventWithViews> toEventWithViewsCollection(Collection<Event> events) {
        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        Predicate wherePredicate = participationRequestUtils.requestsAreApprovedAnd(
                participationRequest.event.id.in(eventIds));
        Map<Long, Long> participationRequestCounts =
                participationRequestRepositoryCustom.getParticipationRequestsCountList(wherePredicate)
                        .stream()
                        .collect(Collectors.toMap(ParticipationRequestCount::getEvent,
                                ParticipationRequestCount::getConfirmedRequests));
        List<EventWithRequests> eventWithRequests = events.stream()
                .map(event -> new EventWithRequests(event, participationRequestCounts.get(event.getId())))
                .collect(Collectors.toList());
        return getEventWithViewsList(eventWithRequests);
    }

    public EventWithViews getEventWithViewsByPredicate(Predicate wherePredicate) {
        List<EventWithRequests> eventWithRequestsList =
                eventRepositoryCustom.getEventWithRequestsList(wherePredicate, null, PageRequest.ofSize(1));
        EventWithViews event;

        if (
                eventWithRequestsList != null
                        && !eventWithRequestsList.isEmpty()
                        && eventWithRequestsList.get(0) != null
        ) {
            event = getEventWithViewsList(eventWithRequestsList).get(0);
        } else {
            event = null;
        }

        return event;
    }

    public List<EventWithViews> getEventWithViewsListByPredicate(Predicate wherePredicate, Predicate havingPredicate,
                                                                 Pageable pageable) {
        return getEventWithViewsList(
                eventRepositoryCustom.getEventWithRequestsList(wherePredicate, havingPredicate, pageable));
    }

}
