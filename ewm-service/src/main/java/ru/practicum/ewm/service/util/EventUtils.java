package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.EwmApplication;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.projection.EventWithRequests;
import ru.practicum.ewm.service.projection.EventWithViews;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.element.model.ElementProjectionMapper.DATE_TIME_FORMATTER;

@Component
public class EventUtils extends ElementUtils<Event> {
    private static final int STATS_MONTHS = 1;
    private final EwmStatsClient ewmStatsClient;
    private final CategoryUtils categoryUtils;

    public EventUtils(EventRepository eventRepository, EwmStatsClient ewmStatsClient, CategoryUtils categoryUtils) {
        super(Event.ELEMENT_NAME, eventRepository);
        this.ewmStatsClient = ewmStatsClient;
        this.categoryUtils = categoryUtils;
    }

    private static String getUri(Long eventId) {
        return "/events/" + eventId;
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

    public List<EventWithViews> getEventWithViewsList(List<EventWithRequests> eventWithRequestsList) {
        Map<String, EventWithRequests> urisEvents = eventWithRequestsList.stream()
                .collect(Collectors.toMap(event -> getUri(event.getId()), event -> event));

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

        List<EventWithViews> eventWithViewsList = new ArrayList<>();

        for (EventWithRequests eventWithRequests : eventWithRequestsList) {
            Long confirmedRequests = eventWithRequests.getConfirmedRequests();
            Long views = urisViews.get(getUri(eventWithRequests.getId()));
            Long likes = eventWithRequests.getLikes();
            Long dislikes = eventWithRequests.getDislikes();

            eventWithViewsList.add(new EventWithViews(
                    eventWithRequests.getEvent(),
                    confirmedRequests != null ? confirmedRequests : 0L,
                    views != null ? views : 0L,
                    likes != null ? likes : 0L,
                    dislikes != null ? dislikes : 0L
            ));
        }

        return eventWithViewsList;
    }

    public void setNewCategory(Event eventToUpdate, Long oldCategoryId, Long newCategoryId) {
        if (newCategoryId != null && !newCategoryId.equals(oldCategoryId)) {
            eventToUpdate.setCategory(categoryUtils.getAndCheck(newCategoryId));
        }
    }
}
