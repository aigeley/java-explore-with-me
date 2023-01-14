package ru.practicum.ewm.model.compilation.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.event.Event;

import java.util.stream.Collectors;

@Component
public class NewCompilationDtoMapper extends ElementDtoMapper<Compilation, NewCompilationDto> {
    public NewCompilationDtoMapper() {
        super(
                NewCompilationDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public NewCompilationDto toDto(Compilation compilation) {
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
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }
}
