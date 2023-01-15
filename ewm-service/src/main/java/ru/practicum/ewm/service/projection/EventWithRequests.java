package ru.practicum.ewm.service.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Identifiable;
import ru.practicum.ewm.model.event.Event;

import java.util.Objects;

@Value
@ToString
public class EventWithRequests implements Identifiable {
    Event event;
    Long confirmedRequests;

    @Override
    public Long getId() {
        return event.getId();
    }

    public void setId(Long id) {
        event.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventWithRequests that = (EventWithRequests) o;
        return Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }
}

