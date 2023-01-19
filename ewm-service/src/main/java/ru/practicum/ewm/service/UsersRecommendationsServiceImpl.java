package ru.practicum.ewm.service;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.EventShortDtoMapper;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.EventWithRequestsRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.projection.EventWithViewsMapper;
import ru.practicum.ewm.service.util.UserUtils;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.ewm.repository.util.QEvent.event;

@AllArgsConstructor
@Slf4j
@Service
public class UsersRecommendationsServiceImpl implements UsersRecommendationsService {
    private final ParticipationRequestRepository participationRequestRepository;
    private final EventRepository eventRepository;
    private final EventWithRequestsRepository eventWithRequestsRepository;
    private final EventWithViewsMapper eventWithViewsMapper;
    private final EventShortDtoMapper eventShortDtoMapper;
    private final UserUtils userUtils;

    @Override
    public List<EventShortDto> getAll(Long userId) {
        userUtils.getAndCheck(userId);
        List<Long> categoryIds = participationRequestRepository.getCategories(userId);

        if (categoryIds == null || categoryIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> eventIds = eventRepository.getRecommendations(categoryIds, userId);

        if (eventIds == null || eventIds.isEmpty()) {
            return new ArrayList<>();
        }

        Predicate wherePredicate = event.id.in(eventIds);
        PageRequestFromElement pageRequest = PageRequestFromElement.of(0, eventIds.size(), new QSort(event.id.asc()));
        return eventShortDtoMapper.toProjectionList(
                eventWithViewsMapper.toProjectionList(
                        eventWithRequestsRepository.getAll(wherePredicate, null, pageRequest)));
    }
}
