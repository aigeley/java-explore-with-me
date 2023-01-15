package ru.practicum.ewm.service;

import ru.practicum.ewm.model.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationsService {
    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto get(Long compId);
}
