package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationsService;

import java.util.List;

@AllArgsConstructor
@RestController
public class CompilationsApiController implements CompilationsApi {
    private final CompilationsService service;

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        return service.getAll(pinned, from, size);
    }

    @Override
    public CompilationDto get(Long compId) {
        return service.get(compId);
    }
}
