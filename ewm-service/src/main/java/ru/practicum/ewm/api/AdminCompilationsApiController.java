package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.model.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.service.AdminCompilationsService;

@AllArgsConstructor
@RestController
public class AdminCompilationsApiController implements AdminCompilationsApi {
    private final AdminCompilationsService service;

    @Override
    public CompilationDto save(NewCompilationDto newCompilationDto) {
        return service.save(newCompilationDto);
    }

    @Override
    public void delete(Long compId) {
        service.delete(compId);
    }

    @Override
    public void removeEvent(Long compId, Long eventId) {
        service.removeEvent(compId, eventId);
    }

    @Override
    public void addEvent(Long compId, Long eventId) {
        service.addEvent(compId, eventId);
    }

    @Override
    public void unpin(Long compId) {
        service.unpin(compId);
    }

    @Override
    public void pin(Long compId) {
        service.pin(compId);
    }
}

