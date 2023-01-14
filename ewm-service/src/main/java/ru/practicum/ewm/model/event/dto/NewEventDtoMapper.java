package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventLocation;

import java.time.LocalDateTime;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;

@Component
public class NewEventDtoMapper extends ElementDtoMapper<Event, NewEventDto> {
    private final LocationMapper locationMapper;

    public NewEventDtoMapper(LocationMapper locationMapper) {
        super(
                NewEventDto.class,
                new TypeReference<>() {
                }
        );
        this.locationMapper = locationMapper;
    }

    @Override
    public NewEventDto toDto(Event event) {
        return event == null ? null : new NewEventDto(
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
    public Event toElement(Event event, NewEventDto newEventDto) {
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), DATE_TIME_FORMATTER));
        event.setLocation(locationMapper.toElement(new EventLocation(), newEventDto.getLocation()));
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        return event;
    }
}
