package ru.practicum.ewm.model.event.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Element;
import ru.practicum.ewm.model.event.Event;

import java.util.Objects;

@Value
@ToString
public class EventWithViews extends Element {
    Event event;
    Long confirmedRequests;
    Long views;

    @Override
    public Long getId() {
        return event.getId();
    }

    @Override
    public void setId(Long id) {
        event.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventWithViews)) {
            return false;
        }
        EventWithViews that = (EventWithViews) o;
        return event.equals(that.event) && confirmedRequests.equals(that.confirmedRequests) && views.equals(that.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, confirmedRequests, views);
    }
}

