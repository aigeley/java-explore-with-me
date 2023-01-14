package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.element.repository.ElementRepositoryAbs;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.projection.EventWithRequests;
import ru.practicum.ewm.model.participation.StatusEnum;
import ru.practicum.ewm.repository.util.QEvent;
import ru.practicum.ewm.repository.util.QParticipationRequest;

import java.util.List;

@Repository
public class EventRepositoryCustomImpl extends ElementRepositoryAbs<Event> implements EventRepositoryCustom {
    public EventRepositoryCustomImpl() {
        super(Event.class);
    }

    @Override
    public List<EventWithRequests> getEventWithRequestsList(Predicate wherePredicate, Predicate havingPredicate,
                                                            Pageable pageable) {
        JPQLQuery<EventWithRequests> query = from(QEvent.event)
                .select(Projections.constructor(EventWithRequests.class, QEvent.event, QParticipationRequest.participationRequest.count()))
                .where(wherePredicate)
                .leftJoin(QParticipationRequest.participationRequest)
                .on(
                        QParticipationRequest.participationRequest.event.id.eq(QEvent.event.id)
                                .and(QParticipationRequest.participationRequest.status.eq(StatusEnum.CONFIRMED))
                )
                .groupBy(QEvent.event)
                .having(havingPredicate);
        return getPageableQuery(query, pageable)
                .fetch();
    }
}
