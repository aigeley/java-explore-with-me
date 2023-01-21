package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.http.EwmStatsClient;

@Component
public class EventUtils extends ElementUtils<Event> {
    private final CategoryUtils categoryUtils;

    public EventUtils(EventRepository eventRepository, EwmStatsClient ewmStatsClient, CategoryUtils categoryUtils) {
        super(Event.ELEMENT_NAME, eventRepository);
        this.categoryUtils = categoryUtils;
    }

    public void setNewCategory(Event eventToUpdate, Long oldCategoryId, Long newCategoryId) {
        if (newCategoryId != null && !newCategoryId.equals(oldCategoryId)) {
            eventToUpdate.setCategory(categoryUtils.getAndCheck(newCategoryId));
        }
    }
}
