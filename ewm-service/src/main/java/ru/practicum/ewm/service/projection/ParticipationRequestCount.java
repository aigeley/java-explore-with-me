package ru.practicum.ewm.service.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Identifiable;

import java.util.Objects;

@Value
@ToString
public class ParticipationRequestCount implements Identifiable {
    Long event;
    Long confirmedRequests;

    @Override
    public Long getId() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipationRequestCount that = (ParticipationRequestCount) o;
        return Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }
}

