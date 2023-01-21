package ru.practicum.ewm.repository.http;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.EwmApplication;
import ru.practicum.ewm.service.projection.EventWithRequests;
import ru.practicum.ewm.service.projection.EventWithViews;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.element.model.ElementProjectionMapper.DATE_TIME_FORMATTER;

@AllArgsConstructor
@Repository
public class EventWithViewsHttpRepositoryImpl implements EventWithViewsHttpRepository {
    private static final int STATS_MONTHS = 1;
    private final EwmStatsClient ewmStatsClient;

    private static String getUri(Long eventId) {
        return "/events/" + eventId;
    }

    private List<ViewStats> getStats(List<String> uris) {
        return ewmStatsClient.getStats(
                LocalDateTime.now()
                        .truncatedTo(ChronoUnit.MICROS)
                        .minusMonths(STATS_MONTHS)
                        .format(DATE_TIME_FORMATTER),
                LocalDateTime.now()
                        .truncatedTo(ChronoUnit.MICROS)
                        .format(DATE_TIME_FORMATTER),
                uris,
                false
        );
    }

    @Override
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

    @Override
    public List<EventWithViews> getAll(List<EventWithRequests> eventWithRequestsList) {
        if (eventWithRequestsList == null || eventWithRequestsList.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> uris = eventWithRequestsList.stream().map(event -> getUri(event.getId()))
                .collect(Collectors.toList());
        List<ViewStats> viewStatsList = getStats(uris);
        Map<String, Long> urisViewsMap = viewStatsList.stream()
                .collect(Collectors.toMap(ViewStats::getUri, ViewStats::getHits));
        List<EventWithViews> eventWithViewsList = new ArrayList<>();

        for (EventWithRequests eventWithRequests : eventWithRequestsList) {
            Long views = urisViewsMap.get(getUri(eventWithRequests.getId()));

            eventWithViewsList.add(new EventWithViews(
                    eventWithRequests.getEvent(),
                    eventWithRequests.getConfirmedRequests(),
                    views == null ? 0L : views,
                    eventWithRequests.getLikes(),
                    eventWithRequests.getDislikes()));
        }

        return eventWithViewsList;
    }

    @Override
    public EventWithViews get(EventWithRequests eventWithRequests) {
        if (eventWithRequests == null) {
            return null;
        }

        List<String> uris = List.of(getUri(eventWithRequests.getId()));
        List<ViewStats> viewStatsList = getStats(uris);

        return new EventWithViews(
                eventWithRequests.getEvent(),
                eventWithRequests.getConfirmedRequests(),
                viewStatsList == null || viewStatsList.isEmpty() ? 0L : viewStatsList.get(0).getHits(),
                eventWithRequests.getLikes(),
                eventWithRequests.getDislikes()
        );
    }
}
