package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.category.dto.CategoryDtoMapper;
import ru.practicum.ewm.model.event.projection.EventWithViews;
import ru.practicum.ewm.model.user.dto.UserShortDtoMapper;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;

@Component
public class EventShortDtoMapper extends ElementDtoMapper<EventWithViews, EventShortDto> {
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
    public EventShortDto toDto(EventWithViews eventWithViews) {
        return eventWithViews == null ? null : new EventShortDto(
                eventWithViews.getEvent().getAnnotation(),
                categoryDtoMapper.toDto(eventWithViews.getEvent().getCategory()),
                eventWithViews.getConfirmedRequests(),
                eventWithViews.getEvent().getEventDate().format(DATE_TIME_FORMATTER),
                eventWithViews.getId(),
                userShortDtoMapper.toDto(eventWithViews.getEvent().getInitiator()),
                eventWithViews.getEvent().getPaid(),
                eventWithViews.getEvent().getTitle(),
                eventWithViews.getViews()
        );
    }
}
