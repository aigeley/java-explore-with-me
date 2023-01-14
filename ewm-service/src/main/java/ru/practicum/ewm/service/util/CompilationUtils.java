package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.compilation.projection.CompilationWithEventViews;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.projection.EventWithViews;
import ru.practicum.ewm.repository.CompilationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CompilationUtils extends ElementUtils<Compilation> {
    private final EventUtils eventUtils;

    public CompilationUtils(CompilationRepository compilationRepository, EventUtils eventUtils) {
        super(Compilation.ELEMENT_NAME, compilationRepository);
        this.eventUtils = eventUtils;
    }

    public CompilationWithEventViews toCompilationWithEventViews(Compilation compilation) {
        Collection<Event> events = compilation.getEvents();
        Set<EventWithViews> eventsWithViews;

        if (events != null && !events.isEmpty()) {
            eventsWithViews = new HashSet<>(eventUtils.toEventWithViewsCollection(events));
        } else {
            eventsWithViews = new HashSet<>();
        }

        return new CompilationWithEventViews(compilation, eventsWithViews);
    }

    public List<CompilationWithEventViews> toCompilationWithEventViewsList(List<Compilation> compilations) {
        Map<Compilation, Collection<EventWithViews>> compilationsWithEventViewsMap = compilations.stream()
                .collect(Collectors.toMap(
                        compilation -> compilation,
                        compilation -> {
                            if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
                                return eventUtils.toEventWithViewsCollection(compilation.getEvents());
                            } else {
                                return new HashSet<>();
                            }
                        }
                ));

        List<CompilationWithEventViews> compilationsWithEventViewsList = new ArrayList<>();

        for (Map.Entry<Compilation, Collection<EventWithViews>> compilationWithEventViewsEntry
                : compilationsWithEventViewsMap.entrySet()) {
            compilationsWithEventViewsList.add(
                    new CompilationWithEventViews(
                            compilationWithEventViewsEntry.getKey(),
                            new HashSet<>(compilationWithEventViewsEntry.getValue())
                    )
            );
        }

        return compilationsWithEventViewsList;
    }
}
