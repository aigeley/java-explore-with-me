package ru.practicum.ewm.service.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Identifiable;
import ru.practicum.ewm.model.event.Event;

import java.util.Objects;

/**
 * Событие с просмотрами
 */
@Value
@ToString
public class EventWithViews implements Identifiable {
    /**
     * Событие
     */
    Event event;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    Long confirmedRequests;

    /**
     * Количество просмотров
     */
    Long views;

    /**
     * Количество лайков
     */
    Long likes;

    /**
     * Количество дизлайков
     */
    Long dislikes;

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
        EventWithViews that = (EventWithViews) o;
        return Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }
}

