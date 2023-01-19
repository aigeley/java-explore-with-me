package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Repository;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.element.repository.ElementRepositoryAbs;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.participation.StatusEnum;
import ru.practicum.ewm.repository.util.QEvent;
import ru.practicum.ewm.repository.util.QMark;
import ru.practicum.ewm.service.projection.EventWithRequests;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.repository.util.QEvent.event;
import static ru.practicum.ewm.repository.util.QParticipationRequest.participationRequest;

@Repository
public class EventWithRequestsRepositoryImpl extends ElementRepositoryAbs<Event>
        implements EventWithRequestsRepository {
    public EventWithRequestsRepositoryImpl() {
        super(Event.class);
    }

    private JPQLQuery<EventWithRequests> getQuery(Predicate wherePredicate, Predicate havingPredicate,
                                                  Pageable pageable) {
        QMark likes = new QMark("likes");
        QMark dislikes = new QMark("dislikes");
        JPQLQuery<EventWithRequests> query = from(event)
                .select(Projections.constructor(EventWithRequests.class, event,
                        participationRequest.id.countDistinct(),
                        likes.id.countDistinct(), dislikes.id.countDistinct()))
                .where(wherePredicate)
                .leftJoin(participationRequest)
                .on(
                        participationRequest.event.id.eq(event.id)
                                .and(participationRequest.status.eq(StatusEnum.CONFIRMED))
                )
                .leftJoin(likes)
                .on(
                        likes.eventId.eq(event.id)
                                .and(likes.markValue.eq(true))
                )
                .leftJoin(dislikes)
                .on(
                        dislikes.eventId.eq(event.id)
                                .and(dislikes.markValue.eq(false))
                )
                .groupBy(event)
                .having(havingPredicate);
        return getPageableQuery(query, pageable);
    }

    @Override
    public List<EventWithRequests> getAll(Predicate wherePredicate, Predicate havingPredicate,
                                          Pageable pageable) {
        return getQuery(wherePredicate, havingPredicate, pageable).fetch();
    }

    @Override
    public EventWithRequests get(Predicate wherePredicate, Predicate havingPredicate,
                                 Pageable pageable) {
        return getQuery(wherePredicate, havingPredicate, pageable).fetchFirst();
    }

    @Override
    public EventWithRequests getByEvent(Event event) {
        if (event == null) {
            return null;
        }

        Predicate wherePredicate = QEvent.event.id.eq(event.getId());
        return get(wherePredicate, null, PageRequest.ofSize(1));
    }

    @Override
    public List<EventWithRequests> getAllByEvents(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        Predicate wherePredicate = event.id.in(eventIds);
        PageRequestFromElement pageRequest = PageRequestFromElement.of(0, events.size(), new QSort(event.id.asc()));
        return getAll(wherePredicate, null, pageRequest);
    }
}
