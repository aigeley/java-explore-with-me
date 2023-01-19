package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventWithRequestsRepository;
import ru.practicum.ewm.repository.http.EventWithViewsHttpRepository;
import ru.practicum.ewm.service.projection.CompilationWithEventViews;
import ru.practicum.ewm.service.projection.EventWithRequests;
import ru.practicum.ewm.service.projection.EventWithViews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CompilationUtils extends ElementUtils<Compilation> {
    private final EventWithRequestsRepository eventWithRequestsRepository;
    private final EventWithViewsHttpRepository eventWithViewsHttpRepository;

    public CompilationUtils(CompilationRepository compilationRepository,
                            EventWithRequestsRepository eventWithRequestsRepository,
                            EventWithViewsHttpRepository eventWithViewsHttpRepository) {
        super(Compilation.ELEMENT_NAME, compilationRepository);
        this.eventWithRequestsRepository = eventWithRequestsRepository;
        this.eventWithViewsHttpRepository = eventWithViewsHttpRepository;
    }

    public CompilationWithEventViews toCompilationWithEventViews(Compilation compilation) {
        if (compilation == null) {
            return null;
        }

        List<Event> events = new ArrayList<>(compilation.getEvents());
        Set<EventWithViews> eventWithViewsSet;

        if (events.isEmpty()) {
            eventWithViewsSet = new HashSet<>();
        } else {
            List<EventWithRequests> eventWithRequestsList =
                    eventWithRequestsRepository.getAllByEvents(events);
            List<EventWithViews> eventWithViewsList = eventWithViewsHttpRepository.getAll(eventWithRequestsList);
            eventWithViewsSet = new HashSet<>(eventWithViewsList);
        }

        return new CompilationWithEventViews(compilation, eventWithViewsSet);
    }

    public List<CompilationWithEventViews> toCompilationWithEventViewsList(List<Compilation> compilations) {
        return compilations == null || compilations.size() == 0 ? Collections.emptyList() : compilations.stream()
                .map(this::toCompilationWithEventViews)
                .collect(Collectors.toList());
    }
}
