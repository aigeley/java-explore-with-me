package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.model.event.projection.EventWithRequests;

import java.util.List;

public interface EventRepositoryCustom {
    List<EventWithRequests> getEventWithRequestsList(Predicate wherePredicate, Predicate havingPredicate,
                                                     Pageable pageable);
}
