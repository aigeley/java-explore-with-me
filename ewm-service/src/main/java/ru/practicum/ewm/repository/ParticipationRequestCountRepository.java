package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.projection.ParticipationRequestCount;

import java.util.List;

@Repository
public interface ParticipationRequestCountRepository {
    List<ParticipationRequestCount> getAll(Predicate wherePredicate);
}
