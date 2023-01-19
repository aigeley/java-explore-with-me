package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.category.dto.CategoryDtoMapper;
import ru.practicum.ewm.model.user.dto.UserShortDtoMapper;
import ru.practicum.ewm.service.projection.EventWithViews;

@Component
public class EventShortDtoMapper extends ElementProjectionMapper<EventWithViews, EventShortDto> {
    private final CategoryDtoMapper categoryDtoMapper;
    private final UserShortDtoMapper userShortDtoMapper;

    public EventShortDtoMapper(CategoryDtoMapper categoryDtoMapper, UserShortDtoMapper userShortDtoMapper) {
        super(
                EventShortDto.class,
                new TypeReference<>() {
                }
        );
        this.categoryDtoMapper = categoryDtoMapper;
        this.userShortDtoMapper = userShortDtoMapper;
    }

    @Override
    public EventShortDto toProjection(EventWithViews eventWithViews) {
        return eventWithViews == null ? null : new EventShortDto(
                eventWithViews.getEvent().getAnnotation(),
                categoryDtoMapper.toProjection(eventWithViews.getEvent().getCategory()),
                eventWithViews.getConfirmedRequests(),
                eventWithViews.getEvent().getEventDate().format(DATE_TIME_FORMATTER),
                eventWithViews.getId(),
                userShortDtoMapper.toProjection(eventWithViews.getEvent().getInitiator()),
                eventWithViews.getEvent().getPaid(),
                eventWithViews.getEvent().getTitle(),
                eventWithViews.getViews(),
                eventWithViews.getLikes(),
                eventWithViews.getDislikes()
        );
    }
}
