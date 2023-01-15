package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import org.springframework.stereotype.Repository;
import ru.practicum.element.repository.ElementRepositoryAbs;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.repository.util.QParticipationRequest;
import ru.practicum.ewm.service.projection.ParticipationRequestCount;

import java.util.List;

@Repository
public class ParticipationRequestCountRepositoryImpl extends ElementRepositoryAbs<ParticipationRequest>
        implements ParticipationRequestCountRepository {
    public ParticipationRequestCountRepositoryImpl() {
        super(ParticipationRequest.class);
    }

    @Override
    public List<ParticipationRequestCount> getAll(Predicate wherePredicate) {
        return from(QParticipationRequest.participationRequest)
                .select(Projections.constructor(ParticipationRequestCount.class,
                        QParticipationRequest.participationRequest.event.id,
                        QParticipationRequest.participationRequest.id.count()))
                .where(wherePredicate)
                .groupBy(QParticipationRequest.participationRequest.event.id)
                .fetch();
    }
}
