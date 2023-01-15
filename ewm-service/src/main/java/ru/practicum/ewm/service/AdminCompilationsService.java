package ru.practicum.ewm.service;

import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.model.compilation.dto.NewCompilationDto;

public interface AdminCompilationsService {
    CompilationDto save(NewCompilationDto newCompilationDto);

    void delete(Long compId);

    void removeEvent(Long compId, Long eventId);

    void addEvent(Long compId, Long eventId);

    void unpin(Long compId);

    void pin(Long compId);
}
