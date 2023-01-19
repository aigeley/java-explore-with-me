package ru.practicum.ewm.service.projection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.QEvent;
import ru.practicum.ewm.repository.EventWithRequestsRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.model.event.QEvent.event;

@Component
public class EventWithRequestsMapper extends ElementProjectionMapper<Event, EventWithRequests> {
    private final EventWithRequestsRepository eventWithRequestsRepository;

    public EventWithRequestsMapper(EventWithRequestsRepository eventWithRequestsRepository) {
        super(
                EventWithRequests.class,
                new TypeReference<>() {
                }
        );
        this.eventWithRequestsRepository = eventWithRequestsRepository;
    }

    @Override
    public EventWithRequests toProjection(Event event) {
        if (event == null) {
            return null;
        }

        Predicate wherePredicate = QEvent.event.id.eq(event.getId());
        return eventWithRequestsRepository.get(wherePredicate, null, PageRequest.ofSize(1));
    }

    @Override
    public List<EventWithRequests> toProjectionList(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        Predicate wherePredicate = event.id.in(eventIds);
        PageRequestFromElement pageRequest = PageRequestFromElement.of(0, events.size(), new QSort(event.id.asc()));
        return eventWithRequestsRepository.getAll(wherePredicate, null, pageRequest);
    }
}
