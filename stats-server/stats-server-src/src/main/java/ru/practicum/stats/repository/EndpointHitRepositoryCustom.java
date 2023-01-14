package ru.practicum.stats.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberExpression;
import ru.practicum.stats.model.ViewStats;

import java.util.List;

public interface EndpointHitRepositoryCustom {
    List<ViewStats> getStats(Predicate wherePredicate, NumberExpression<Long> countExpression);
}
