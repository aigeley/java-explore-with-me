package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.participation.projection.ParticipationRequestCount;

import java.util.List;

@Repository
public interface ParticipationRequestRepositoryCustom {
    List<ParticipationRequestCount> getParticipationRequestsCountList(Predicate wherePredicate);
}
