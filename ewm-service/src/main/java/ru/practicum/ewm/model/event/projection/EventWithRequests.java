package ru.practicum.ewm.model.event.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Element;
import ru.practicum.ewm.model.event.Event;

import java.util.Objects;

@Value
@ToString
public class EventWithRequests extends Element {
    Event event;
    Long confirmedRequests;

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
        if (!(o instanceof EventWithRequests)) {
            return false;
        }
        EventWithRequests that = (EventWithRequests) o;
        return event.equals(that.event) && confirmedRequests.equals(that.confirmedRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, confirmedRequests);
    }
}

