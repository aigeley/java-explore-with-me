package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.model.compilation.dto.CompilationDtoMapper;
import ru.practicum.ewm.model.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.model.compilation.dto.NewCompilationDtoMapper;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.service.util.CompilationUtils;
import ru.practicum.ewm.service.util.EventUtils;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Service
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    private final CompilationRepository compilationRepository;
    private final CompilationUtils compilationUtils;
    private final CompilationDtoMapper compilationDtoMapper;
    private final NewCompilationDtoMapper newCompilationDtoMapper;
    private final EventUtils eventUtils;

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = newCompilationDtoMapper.toElement(new Compilation(), newCompilationDto);
        Set<Event> events = new HashSet<>(eventUtils.getAndCheckElementCollection(newCompilationDto.getEvents()));
        compilation.setEvents(events);
        Compilation compilationSaved = compilationRepository.save(compilation);
        log.info("saveCompilation: " + compilationSaved);
        return compilationDtoMapper.toDto(compilationUtils.toCompilationWithEventViews(compilationSaved));
    }

    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = compilationUtils.getAndCheckElement(compId);
        compilationRepository.delete(compilation);
        log.info("deleteCompilation: " + compilation);
    }

    @Override
    public void removeEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationUtils.getAndCheckElement(compId);
        Event event = eventUtils.getAndCheckElement(eventId);
        Set<Event> events = new HashSet<>(compilation.getEvents());
        events.remove(event);
        compilation.setEvents(events);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("removeEventFromCompilation: remove Event id = {} from Compilation {}", eventId, compilationUpdated);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationUtils.getAndCheckElement(compId);
        Event event = eventUtils.getAndCheckElement(eventId);
        Set<Event> events = new HashSet<>(compilation.getEvents());
        events.add(event);
        compilation.setEvents(events);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("addEventToCompilation: add Event id = {} to Compilation {}", eventId, compilationUpdated);
    }

    @Override
    public void unpin(Long compId) {
        Compilation compilation = compilationUtils.getAndCheckElement(compId);
        compilation.setPinned(false);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("unpin: " + compilationUpdated);
    }

    @Override
    public void pin(Long compId) {
        Compilation compilation = compilationUtils.getAndCheckElement(compId);
        compilation.setPinned(true);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("pin: " + compilationUpdated);
    }
}
