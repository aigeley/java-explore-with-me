package ru.practicum.ewm.model.participation.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Element;

import java.util.Objects;

@Value
@ToString
public class ParticipationRequestCount extends Element {
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
        ParticipationRequestCount participationRequestCount = (ParticipationRequestCount) o;
        return Objects.equals(this.event, participationRequestCount.event) &&
                Objects.equals(this.confirmedRequests, participationRequestCount.confirmedRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, confirmedRequests);
    }
}

