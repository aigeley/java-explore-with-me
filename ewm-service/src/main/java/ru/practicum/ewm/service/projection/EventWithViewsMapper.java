package ru.practicum.ewm.service.projection;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.service.util.EventUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class EventWithViewsMapper extends ElementProjectionMapper<EventWithRequests, EventWithViews> {
    private final EventUtils eventUtils;

    public EventWithViewsMapper(EventUtils eventUtils) {
        super(
                EventWithViews.class,
                new TypeReference<>() {
                }
        );
        this.eventUtils = eventUtils;
    }

    @Override
    public EventWithViews toProjection(EventWithRequests eventWithRequests) {
        if (eventWithRequests == null) {
            return null;
        }

        List<EventWithViews> eventWithViewsList = eventUtils.getEventWithViewsList(Arrays.asList(eventWithRequests));

        EventWithViews eventWithViews;

        if (eventWithViewsList == null || eventWithViewsList.size() == 0) {
            eventWithViews = null;
        } else {
            eventWithViews = eventWithViewsList.get(0);
        }

        return eventWithViews;
    }

    @Override
    public List<EventWithViews> toProjectionList(List<EventWithRequests> eventWithRequestsList) {
        if (eventWithRequestsList == null || eventWithRequestsList.size() == 0) {
            return Collections.emptyList();
        }

        return eventUtils.getEventWithViewsList(eventWithRequestsList);
    }
}
