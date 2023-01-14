package ru.practicum.ewm.model.compilation.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.compilation.projection.CompilationWithEventViews;
import ru.practicum.ewm.model.event.dto.EventShortDtoMapper;

import java.util.stream.Collectors;

@Component
public class CompilationDtoMapper extends ElementDtoMapper<CompilationWithEventViews, CompilationDto> {
    private final EventShortDtoMapper eventShortDtoMapper;

    public CompilationDtoMapper(EventShortDtoMapper eventShortDtoMapper) {
        super(
                CompilationDto.class,
                new TypeReference<>() {
                }
        );
        this.eventShortDtoMapper = eventShortDtoMapper;
    }

    @Override
    public CompilationDto toDto(CompilationWithEventViews compilationWithEventViews) {
        return compilationWithEventViews == null ? null : new CompilationDto(
                compilationWithEventViews.getEventsWithViews().stream()
                        .map(eventShortDtoMapper::toDto)
                        .collect(Collectors.toSet()),
                compilationWithEventViews.getId(),
                compilationWithEventViews.getCompilation().getPinned(),
                compilationWithEventViews.getCompilation().getTitle()
        );
    }
}
