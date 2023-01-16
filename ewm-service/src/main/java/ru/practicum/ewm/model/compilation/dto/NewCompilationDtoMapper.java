package ru.practicum.ewm.model.compilation.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.event.Event;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NewCompilationDtoMapper extends ElementProjectionMapper<Compilation, NewCompilationDto> {
    public NewCompilationDtoMapper() {
        super(
                NewCompilationDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public NewCompilationDto toProjection(Compilation compilation) {
        return compilation == null ? null : new NewCompilationDto(
                compilation.getEvents().stream()
                        .map(Event::getId)
                        .collect(Collectors.toSet()),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }

    @Override
    public Compilation toElement(Compilation compilation, NewCompilationDto newCompilationDto) {
        Optional.ofNullable(newCompilationDto.getPinned()).ifPresent(compilation::setPinned);
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }
}
