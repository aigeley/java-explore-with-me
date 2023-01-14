package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.event.Event;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;

@Component
public class UpdateEventRequestMapper extends ElementDtoMapper<Event, UpdateEventRequest> {
    public UpdateEventRequestMapper() {
        super(
                UpdateEventRequest.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public UpdateEventRequest toDto(Event event) {
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
        Optional.ofNullable(updateEventRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updateEventRequest.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(updateEventRequest.getEventDate())
                .ifPresent(eventDate -> event.setEventDate(LocalDateTime.parse(eventDate, DATE_TIME_FORMATTER)));
        Optional.ofNullable(updateEventRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(updateEventRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(updateEventRequest.getTitle()).ifPresent(event::setTitle);
        return event;
    }
}
