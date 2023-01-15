package ru.practicum.stats.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.model.EndpointHitEntity;
import ru.practicum.stats.model.ViewStats;

import java.util.List;

import static ru.practicum.stats.repository.util.QEndpointHitEntity.endpointHitEntity;

@Repository
public class ViewStatsRepositoryImpl extends QuerydslRepositorySupport implements ViewStatsRepository {
    public ViewStatsRepositoryImpl() {
        super(EndpointHitEntity.class);
    }

    @Override
    public List<ViewStats> getAll(Predicate wherePredicate, NumberExpression<Long> countExpression) {
        return from(endpointHitEntity)
                .select(Projections.constructor(ViewStats.class, endpointHitEntity.app, endpointHitEntity.uri,
                        countExpression))
                .where(wherePredicate)
                .groupBy(endpointHitEntity.app, endpointHitEntity.uri)
                .orderBy(endpointHitEntity.app.asc(), endpointHitEntity.uri.asc())
                .fetch();
    }
}
