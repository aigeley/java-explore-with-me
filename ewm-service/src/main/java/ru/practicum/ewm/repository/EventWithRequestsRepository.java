package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.service.projection.EventWithRequests;

import java.util.List;

public interface EventWithRequestsRepository {
    List<EventWithRequests> getAll(Predicate wherePredicate, Predicate havingPredicate,
                                   Pageable pageable);

    EventWithRequests get(Predicate wherePredicate, Predicate havingPredicate,
                          Pageable pageable);

    EventWithRequests getByEvent(Event event);

    List<EventWithRequests> getAllByEvents(List<Event> events);
}
