package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.service.projection.EventWithRequests;

import java.util.List;

public interface EventWithRequestsRepository {
    List<EventWithRequests> getAll(Predicate wherePredicate, Predicate havingPredicate,
                                   Pageable pageable);
}