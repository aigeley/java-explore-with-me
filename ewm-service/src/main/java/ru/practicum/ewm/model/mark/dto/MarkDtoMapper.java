package ru.practicum.ewm.model.mark.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.mark.Mark;

@Component
public class MarkDtoMapper
        extends ElementProjectionMapper<Mark, MarkDto> {
    public MarkDtoMapper() {
        super(
                MarkDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public MarkDto toProjection(Mark mark) {
        return mark == null ? null : new MarkDto(
                mark.getId(),
                mark.getCreated().format(DATE_TIME_FORMATTER),
                mark.getEventId(),
                mark.getUserId(),
                mark.getMarkValue()
        );
    }
}
