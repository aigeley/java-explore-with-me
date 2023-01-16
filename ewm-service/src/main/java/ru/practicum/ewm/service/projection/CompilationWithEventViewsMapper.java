package ru.practicum.ewm.service.projection;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.event.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CompilationWithEventViewsMapper extends ElementProjectionMapper<Compilation, CompilationWithEventViews> {
    private final EventWithRequestsMapper eventWithRequestsMapper;
    private final EventWithViewsMapper eventWithViewsMapper;

    public CompilationWithEventViewsMapper(EventWithRequestsMapper eventWithRequestsMapper,
                                           EventWithViewsMapper eventWithViewsMapper) {
        super(
                CompilationWithEventViews.class,
                new TypeReference<>() {
                }
        );
        this.eventWithRequestsMapper = eventWithRequestsMapper;
        this.eventWithViewsMapper = eventWithViewsMapper;
    }

    @Override
    public CompilationWithEventViews toProjection(Compilation compilation) {
        if (compilation == null) {
            return null;
        }

        List<Event> events = new ArrayList<>(compilation.getEvents());
        Set<EventWithViews> eventWithViewsSet;

        if (events.isEmpty()) {
            eventWithViewsSet = new HashSet<>();
        } else {
            eventWithViewsSet = new HashSet<>(
                    eventWithViewsMapper.toProjectionList(
                            eventWithRequestsMapper.toProjectionList(events)));
        }

        return new CompilationWithEventViews(compilation, eventWithViewsSet);
    }
}
