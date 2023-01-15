package ru.practicum.ewm.service.projection;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.repository.ParticipationRequestCountRepository;
import ru.practicum.ewm.service.util.ParticipationRequestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventWithRequestsMapper extends ElementProjectionMapper<Event, EventWithRequests> {
    private final ParticipationRequestUtils participationRequestUtils;

    public EventWithRequestsMapper(ParticipationRequestUtils participationRequestUtils,
                                   ParticipationRequestCountRepository participationRequestCountRepository) {
        super(
                EventWithRequests.class,
                new TypeReference<>() {
                }
        );
        this.participationRequestUtils = participationRequestUtils;
    }

    @Override
    public EventWithRequests toProjection(Event event) {
        return event == null ? null : new EventWithRequests(
                event,
                participationRequestUtils.getConfirmedRequests(event.getId())
        );
    }

    @Override
    public List<EventWithRequests> toProjectionList(List<Event> events) {
        if (events == null || events.size() == 0) {
            return Collections.emptyList();
        }

        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        Map<Long, Long> participationRequestCountMap = participationRequestUtils.getConfirmedRequestsMap(eventIds);

        return events.stream()
                .map(event -> new EventWithRequests(event, participationRequestCountMap.get(event.getId())))
                .collect(Collectors.toList());
    }
}
