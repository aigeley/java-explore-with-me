package ru.practicum.stats.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.model.ViewStats;
import ru.practicum.stats.repository.ViewStatsRepository;
import ru.practicum.stats.repository.util.QEndpointHitEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.element.model.ElementProjectionMapper.DATE_TIME_FORMATTER;

@AllArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {
    private final ViewStatsRepository viewStatsRepository;

    @Override
    public List<ViewStats> getAll(String start, String end, List<String> uris, Boolean unique) {
        List<Predicate> wherePredicates = new ArrayList<>();

        if (start != null) {
            wherePredicates.add(QEndpointHitEntity.endpointHitEntity.timestamp.goe(
                    LocalDateTime.parse(start, DATE_TIME_FORMATTER)));
        }

        if (end != null) {
            wherePredicates.add(
                    QEndpointHitEntity.endpointHitEntity.timestamp.loe(LocalDateTime.parse(end, DATE_TIME_FORMATTER)));
        }

        if (uris != null && !uris.isEmpty()) {
            wherePredicates.add(QEndpointHitEntity.endpointHitEntity.uri.in(uris));
        }

        NumberExpression<Long> countExpression;

        if (unique != null && unique) {
            countExpression = QEndpointHitEntity.endpointHitEntity.ip.countDistinct();
        } else {
            countExpression = QEndpointHitEntity.endpointHitEntity.ip.count();
        }

        Predicate wherePredicate = ExpressionUtils.allOf(wherePredicates);
        return viewStatsRepository.getAll(wherePredicate, countExpression);
    }
}
