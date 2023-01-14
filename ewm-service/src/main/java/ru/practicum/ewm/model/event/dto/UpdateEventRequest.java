package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Данные для изменения информации о событии
 */
@Schema(name = "UpdateEventRequest", description = "Данные для изменения информации о событии")
@Value
@ToString
public class UpdateEventRequest {
    /**
     * Новая аннотация
     */
    @Size(min = 20, max = 2000)
    @Schema(name = "annotation", example = "Сап прогулки по рекам и каналам – это возможность увидеть Практикбург с другого ракурса", description = "Новая аннотация", required = false)
    @JsonProperty("annotation")
    String annotation;

    /**
     * Новая категория
     */
    @Schema(name = "category", example = "3", description = "Новая категория", required = false)
    @JsonProperty("category")
    Long category;

    /**
     * Новое описание
     */
    @Size(min = 20, max = 7000)
    @Schema(name = "description", example = "От английского SUP - Stand Up Paddle — \"стоя на доске с веслом\", гавайская разновидность сёрфинга, в котором серфер, стоя на доске, катается на волнах и при этом гребет веслом, а не руками, как в классическом серфинге.", description = "Новое описание", required = false)
    @JsonProperty("description")
    String description;

    /**
     * Новые дата и время на которые намечено событие. Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"
     */
    @Schema(name = "eventDate", example = "2023-10-11 23:10:05", description = "Новые дата и время на которые намечено событие. Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"", required = false)
    @JsonProperty("eventDate")
    String eventDate;

    /**
     * Идентификатор события
     */
    @NotNull
    @Schema(name = "eventId", example = "1", description = "Идентификатор события", required = true)
    @JsonProperty("eventId")
    Long eventId;

    /**
     * Новое значение флага о платности мероприятия
     */
    @Schema(name = "paid", example = "true", description = "Новое значение флага о платности мероприятия", required = false)
    @JsonProperty("paid")
    Boolean paid;

    /**
     * Новый лимит пользователей
     */
    @Schema(name = "participantLimit", example = "7", description = "Новый лимит пользователей", required = false)
    @JsonProperty("participantLimit")
    Integer participantLimit;

    /**
     * Новый заголовок
     */
    @Size(min = 3, max = 120)
    @Schema(name = "title", example = "Сап прогулки по рекам и каналам", description = "Новый заголовок", required = false)
    @JsonProperty("title")
    String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateEventRequest updateEventRequest = (UpdateEventRequest) o;
        return Objects.equals(this.annotation, updateEventRequest.annotation) &&
                Objects.equals(this.category, updateEventRequest.category) &&
                Objects.equals(this.description, updateEventRequest.description) &&
                Objects.equals(this.eventDate, updateEventRequest.eventDate) &&
                Objects.equals(this.eventId, updateEventRequest.eventId) &&
                Objects.equals(this.paid, updateEventRequest.paid) &&
                Objects.equals(this.participantLimit, updateEventRequest.participantLimit) &&
                Objects.equals(this.title, updateEventRequest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, eventId, paid, participantLimit, title);
    }
}

