package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventLocation;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;

@Component
public class AdminUpdateEventRequestMapper extends ElementDtoMapper<Event, AdminUpdateEventRequest> {
    private final LocationMapper locationMapper;

    public AdminUpdateEventRequestMapper(LocationMapper locationMapper) {
        super(
                AdminUpdateEventRequest.class,
                new TypeReference<>() {
                }
        );
        this.locationMapper = locationMapper;
    }

    @Override
    public AdminUpdateEventRequest toDto(Event event) {
        return event == null ? null : new AdminUpdateEventRequest(
                event.getAnnotation(),
                event.getCategory().getId(),
                event.getDescription(),
                event.getEventDate().format(DATE_TIME_FORMATTER),
                locationMapper.toDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getRequestModeration(),
                event.getTitle()
        );
    }

    @Override
    public Event toElement(Event event, AdminUpdateEventRequest adminUpdateEventRequest) {
        Optional.ofNullable(adminUpdateEventRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(adminUpdateEventRequest.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(adminUpdateEventRequest.getEventDate())
                .ifPresent(eventDate -> event.setEventDate(LocalDateTime.parse(eventDate, DATE_TIME_FORMATTER)));
        Optional.ofNullable(adminUpdateEventRequest.getLocation())
                .ifPresent(location -> event.setLocation(locationMapper.toElement(new EventLocation(), location)));
        Optional.ofNullable(adminUpdateEventRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(adminUpdateEventRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(adminUpdateEventRequest.getRequestModeration()).ifPresent(event::setRequestModeration);
        Optional.ofNullable(adminUpdateEventRequest.getTitle()).ifPresent(event::setTitle);
        return event;
    }
}
