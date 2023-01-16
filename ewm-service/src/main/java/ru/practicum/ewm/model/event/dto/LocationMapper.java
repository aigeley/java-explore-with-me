package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.event.EventLocation;

@Component
public class LocationMapper extends ElementProjectionMapper<EventLocation, Location> {
    public LocationMapper() {
        super(
                Location.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public Location toProjection(EventLocation eventLocation) {
        return eventLocation == null ? null : new Location(
                eventLocation.getLat(),
                eventLocation.getLon()
        );
    }

    @Override
    public EventLocation toElement(EventLocation eventLocation, Location location) {
        eventLocation.setLat(location.getLat());
        eventLocation.setLon(location.getLon());
        return eventLocation;
    }
}
