package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Новое событие
 */
@Schema(name = "NewEventDto", description = "Новое событие")
@Value
@ToString
public class NewEventDto {
    /**
     * Краткое описание события
     */
    @NotBlank
    @Size(min = 20, max = 2000)
    @Schema(
            name = "annotation",
            example = "Сплав на байдарках похож на полет.",
            description = "Краткое описание события",
            required = true
    )
    @JsonProperty("annotation")
    String annotation;

    /**
     * id категории к которой относится событие
     */
    @NotNull
    @Schema(name = "category", example = "2", description = "id категории к которой относится событие", required = true)
    @JsonProperty("category")
    Long category;

    /**
     * Полное описание события
     */
    @NotBlank
    @Size(min = 20, max = 7000)
    @Schema(
            name = "description",
            example = "Сплав на байдарках похож на полет. На спокойной воде — это парение. " +
                    "На бурной, порожистой — выполнение фигур высшего пилотажа. " +
                    "И то, и другое дарят чувство обновления, феерические эмоции, яркие впечатления.",
            description = "Полное описание события",
            required = true
    )
    @JsonProperty("description")
    String description;

    /**
     * Дата и время на которые намечено событие. Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"
     */
    @NotNull
    @Schema(
            name = "eventDate",
            example = "2024-12-31 15:10:05",
            description = "Дата и время на которые намечено событие. " +
                    "Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"",
            required = true
    )
    @JsonProperty("eventDate")
    String eventDate;

    /**
     * Get location
     */
    @NotNull
    @Valid
    @Schema(name = "location", required = true)
    @JsonProperty("location")
    Location location;

    /**
     * Нужно ли оплачивать участие в событии
     */
    @Schema(name = "paid", example = "true", description = "Нужно ли оплачивать участие в событии", required = false)
    @JsonProperty(value = "paid", defaultValue = "false")
    Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Schema(
            name = "participantLimit",
            example = "10",
            description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            required = false
    )
    @JsonProperty(value = "participantLimit", defaultValue = "0")
    Integer participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие.
     * Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    @Schema(
            name = "requestModeration",
            example = "false",
            description = "Нужна ли пре-модерация заявок на участие. " +
                    "Если true, то все заявки будут ожидать подтверждения инициатором события. " +
                    "Если false - то будут подтверждаться автоматически.",
            required = false
    )
    @JsonProperty(value = "requestModeration", defaultValue = "true")
    Boolean requestModeration;

    /**
     * Заголовок события
     */
    @NotBlank
    @Size(min = 3, max = 120)
    @Schema(name = "title", example = "Сплав на байдарках", description = "Заголовок события", required = true)
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
        NewEventDto newEventDto = (NewEventDto) o;
        return Objects.equals(this.annotation, newEventDto.annotation) &&
                Objects.equals(this.category, newEventDto.category) &&
                Objects.equals(this.description, newEventDto.description) &&
                Objects.equals(this.eventDate, newEventDto.eventDate) &&
                Objects.equals(this.location, newEventDto.location) &&
                Objects.equals(this.paid, newEventDto.paid) &&
                Objects.equals(this.participantLimit, newEventDto.participantLimit) &&
                Objects.equals(this.requestModeration, newEventDto.requestModeration) &&
                Objects.equals(this.title, newEventDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, location, paid, participantLimit,
                requestModeration, title);
    }
}

