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
import ru.practicum.ewm.service.projection.CompilationWithEventViewsMapper;
import ru.practicum.ewm.service.util.CompilationUtils;
import ru.practicum.ewm.service.util.EventUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Transactional
@Service
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    private final CompilationRepository compilationRepository;
    private final CompilationUtils compilationUtils;
    private final CompilationDtoMapper compilationDtoMapper;
    private final NewCompilationDtoMapper newCompilationDtoMapper;
    private final CompilationWithEventViewsMapper compilationWithEventViewsMapper;
    private final EventUtils eventUtils;

    @Override
    public CompilationDto save(NewCompilationDto newCompilationDto) {
        Compilation compilation = newCompilationDtoMapper.toElement(new Compilation(), newCompilationDto);
        Set<Event> events = new HashSet<>(eventUtils.getAllAndCheck(newCompilationDto.getEvents()));
        compilation.setEvents(events);
        Compilation compilationSaved = compilationRepository.save(compilation);
        log.info("saveCompilation: " + compilationSaved);
        return compilationDtoMapper.toProjection(
                compilationWithEventViewsMapper.toProjection(compilationSaved));
    }

    @Override
    public void delete(Long compId) {
        Compilation compilation = compilationUtils.getAndCheck(compId);
        compilationRepository.delete(compilation);
        log.info("deleteCompilation: " + compilation);
    }

    @Override
    public void removeEvent(Long compId, Long eventId) {
        Compilation compilation = compilationUtils.getAndCheck(compId);
        Event event = eventUtils.getAndCheck(eventId);
        Set<Event> events = new HashSet<>(compilation.getEvents());
        events.remove(event);
        compilation.setEvents(events);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("removeEventFromCompilation: remove Event id = {} from Compilation {}", eventId, compilationUpdated);
    }

    @Override
    public void addEvent(Long compId, Long eventId) {
        Compilation compilation = compilationUtils.getAndCheck(compId);
        Event event = eventUtils.getAndCheck(eventId);
        Set<Event> events = new HashSet<>(compilation.getEvents());
        events.add(event);
        compilation.setEvents(events);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("addEventToCompilation: add Event id = {} to Compilation {}", eventId, compilationUpdated);
    }

    @Override
    public void unpin(Long compId) {
        Compilation compilation = compilationUtils.getAndCheck(compId);
        compilation.setPinned(false);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("unpin: " + compilationUpdated);
    }

    @Override
    public void pin(Long compId) {
        Compilation compilation = compilationUtils.getAndCheck(compId);
        compilation.setPinned(true);
        Compilation compilationUpdated = compilationRepository.save(compilation);
        log.info("pin: " + compilationUpdated);
    }
}
