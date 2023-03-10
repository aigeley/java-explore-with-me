package ru.practicum.ewm.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.model.event.SortEnum;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventFullDtoMapper;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.EventShortDtoMapper;
import ru.practicum.ewm.repository.EventWithRequestsRepository;
import ru.practicum.ewm.repository.http.EventWithViewsHttpRepository;
import ru.practicum.ewm.repository.util.QMark;
import ru.practicum.ewm.service.projection.EventWithRequests;
import ru.practicum.ewm.service.projection.EventWithViews;
import ru.practicum.ewm.service.util.EventUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ru.practicum.element.model.ElementProjectionMapper.DATE_TIME_FORMATTER;
import static ru.practicum.ewm.repository.util.QEvent.event;
import static ru.practicum.ewm.repository.util.QParticipationRequest.participationRequest;

@AllArgsConstructor
@Service
public class EventsServiceImpl implements EventsService {
    private final EventUtils eventUtils;
    private final EventWithRequestsRepository eventWithRequestsRepository;
    private final EventWithViewsHttpRepository eventWithViewsHttpRepository;
    private final EventFullDtoMapper eventFullDtoMapper;
    private final EventShortDtoMapper eventShortDtoMapper;

    @Override
    public List<EventShortDto> getAll(String text, List<Long> categories, Boolean paid, String rangeStart,
                                      String rangeEnd, Boolean onlyAvailable, SortEnum sort, Integer from,
                                      Integer size, String ip) {
        List<Predicate> wherePredicates = new ArrayList<>();
        wherePredicates.add(event.state.eq(StateEnum.PUBLISHED));

        if (text != null && !text.isBlank()) {
            wherePredicates.add(ExpressionUtils.anyOf(
                    event.annotation.containsIgnoreCase(text),
                    event.description.containsIgnoreCase(text),
                    event.title.containsIgnoreCase(text)
            ));
        }

        if (categories != null && !categories.isEmpty()) {
            wherePredicates.add(event.category.id.in(categories));
        }

        if (paid != null) {
            if (paid) {
                wherePredicates.add(event.paid.isTrue());
            } else {
                wherePredicates.add(event.paid.isFalse());
            }
        }

        if (rangeStart != null) {
            wherePredicates.add(event.eventDate.goe(LocalDateTime.parse(rangeStart, DATE_TIME_FORMATTER)));
        } else {
            //???? ??????????????????, ?????????????????? ?????????????? ??????????????
            wherePredicates.add(event.eventDate.goe(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));
        }

        if (rangeEnd != null) {
            wherePredicates.add(event.eventDate.loe(LocalDateTime.parse(rangeEnd, DATE_TIME_FORMATTER)));
        }

        Predicate wherePredicate = ExpressionUtils.allOf(wherePredicates);
        Predicate havingPredicate;

        if (onlyAvailable != null && onlyAvailable) {
            havingPredicate = participationRequest.id.count().loe(event.participantLimit)
                    .or(event.participantLimit.eq(0));
        } else {
            havingPredicate = null;
        }

        QSort qSort;

        if (sort == SortEnum.EVENT_DATE) {
            //???????????????????? ???? ???????? ??????????????, ???? ?????????????????? ?? ?????????? ??????????????
            qSort = new QSort(event.eventDate.asc(), event.id.asc());
        } else if (sort == SortEnum.LIKES) {
            //???????????????????? ???? ?????????? ????????????, ???? ???????????????? ?????????? ?? ????????????????
            QMark likes = new QMark("likes");
            qSort = new QSort(likes.id.count().desc(), event.id.asc());
        } else {
            //???? ??????????????????, ???????????????????? ?? ?????????????? ???????????????? ??????????????, ???? ???????????? ?? ??????????
            qSort = new QSort(event.id.asc());
        }

        PageRequestFromElement pageRequest = PageRequestFromElement.of(from, size, qSort);
        List<EventWithRequests> eventWithRequestsList =
                eventWithRequestsRepository.getAll(wherePredicate, havingPredicate, pageRequest);
        List<EventWithViews> eventWithViewsList = eventWithViewsHttpRepository.getAll(eventWithRequestsList);

        if (sort == SortEnum.VIEWS) {
            //???????????????????? ???? ?????????? ????????????????????, ???? ???????????????? ?????????? ?? ???????????????? (???????????? ?????????????? ????????????????)
            eventWithViewsList.sort(Comparator.comparing(EventWithViews::getViews, Comparator.reverseOrder()));
        }

        eventWithViewsHttpRepository.hit("/events", ip);
        return eventShortDtoMapper.toProjectionList(eventWithViewsList);
    }

    @Override
    public EventFullDto get(Long id, String ip) {
        Predicate wherePredicate = ExpressionUtils.allOf(
                event.id.eq(id),
                event.state.eq(StateEnum.PUBLISHED)
        );
        EventWithRequests eventWithRequests =
                eventWithRequestsRepository.get(wherePredicate, null, PageRequest.ofSize(1));
        eventWithViewsHttpRepository.hit("/events/" + id, ip);
        return eventFullDtoMapper.toProjection(
                eventWithViewsHttpRepository.get(eventWithRequests));
    }
}
