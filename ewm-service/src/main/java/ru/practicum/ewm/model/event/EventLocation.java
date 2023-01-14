package ru.practicum.ewm.model.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@ToString
@Getter
@Setter
public class EventLocation {

    @Column(name = "location_lat")
    private Float lat;

    @Column(name = "location_lon")
    private Float lon;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventLocation eventLocation = (EventLocation) o;
        return Objects.equals(this.lat, eventLocation.lat) &&
                Objects.equals(this.lon, eventLocation.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}

