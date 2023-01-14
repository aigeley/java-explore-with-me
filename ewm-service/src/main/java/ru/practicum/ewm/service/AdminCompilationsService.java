package ru.practicum.ewm.service;

import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.model.compilation.dto.NewCompilationDto;

public interface AdminCompilationsService {
    CompilationDto saveCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    void removeEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpin(Long compId);

    void pin(Long compId);
}
