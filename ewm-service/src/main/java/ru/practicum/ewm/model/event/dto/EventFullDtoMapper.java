package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.category.dto.CategoryDtoMapper;
import ru.practicum.ewm.model.event.projection.EventWithViews;
import ru.practicum.ewm.model.user.dto.UserShortDtoMapper;

import java.util.Optional;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;

@Component
public class EventFullDtoMapper extends ElementDtoMapper<EventWithViews, EventFullDto> {
    private final CategoryDtoMapper categoryDtoMapper;
    private final UserShortDtoMapper userShortDtoMapper;
    private final LocationMapper locationMapper;

    public EventFullDtoMapper(CategoryDtoMapper categoryDtoMapper, UserShortDtoMapper userShortDtoMapper,
                              LocationMapper locationMapper) {
        super(
                EventFullDto.class,
                new TypeReference<>() {
                }
        );
        this.categoryDtoMapper = categoryDtoMapper;
        this.userShortDtoMapper = userShortDtoMapper;
        this.locationMapper = locationMapper;
    }

    @Override
    public EventFullDto toDto(EventWithViews eventWithViews) {
        return eventWithViews == null ? null : new EventFullDto(
                eventWithViews.getEvent().getAnnotation(),
                categoryDtoMapper.toDto(eventWithViews.getEvent().getCategory()),
                eventWithViews.getConfirmedRequests(),
                eventWithViews.getEvent().getCreatedOn().format(DATE_TIME_FORMATTER),
                eventWithViews.getEvent().getDescription(),
                eventWithViews.getEvent().getEventDate().format(DATE_TIME_FORMATTER),
                eventWithViews.getId(),
                userShortDtoMapper.toDto(eventWithViews.getEvent().getInitiator()),
                locationMapper.toDto(eventWithViews.getEvent().getLocation()),
                eventWithViews.getEvent().getPaid(),
                eventWithViews.getEvent().getParticipantLimit(),
                Optional.ofNullable(eventWithViews.getEvent().getPublishedOn())
                        .map(publishedOn -> publishedOn.format(DATE_TIME_FORMATTER))
                        .orElse(null),
                eventWithViews.getEvent().getRequestModeration(),
                eventWithViews.getEvent().getState(),
                eventWithViews.getEvent().getTitle(),
                eventWithViews.getViews()
        );
    }
}
