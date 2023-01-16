package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.event.Event;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UpdateEventRequestMapper extends ElementProjectionMapper<Event, UpdateEventRequest> {
    public UpdateEventRequestMapper() {
        super(
                UpdateEventRequest.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public UpdateEventRequest toProjection(Event event) {
        return event == null ? null : new UpdateEventRequest(
                event.getAnnotation(),
                event.getCategory().getId(),
                event.getDescription(),
                event.getEventDate().format(DATE_TIME_FORMATTER),
                event.getId(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getTitle()
        );
    }

    @Override
    public Event toElement(Event event, UpdateEventRequest updateEventRequest) {
        Optional.ofNullable(getNullIfBlank(updateEventRequest.getAnnotation())).ifPresent(event::setAnnotation);
        Optional.ofNullable(getNullIfBlank(updateEventRequest.getDescription())).ifPresent(event::setDescription);
        Optional.ofNullable(updateEventRequest.getEventDate())
                .ifPresent(eventDate -> event.setEventDate(LocalDateTime.parse(eventDate, DATE_TIME_FORMATTER)));
        Optional.ofNullable(updateEventRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(updateEventRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(getNullIfBlank(updateEventRequest.getTitle())).ifPresent(event::setTitle);
        return event;
    }
}
