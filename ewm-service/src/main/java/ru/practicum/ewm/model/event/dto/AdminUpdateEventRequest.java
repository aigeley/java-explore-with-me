package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * Информация для редактирования события администратором. Все поля необязательные. Значение полей не валидируется.
 */
@Schema(name = "AdminUpdateEventRequest",
        description = "Информация для редактирования события администратором. Все поля необязательные. " +
                "Значение полей не валидируется.")
@Value
@ToString
public class AdminUpdateEventRequest {
    /**
     * Краткое описание события
     */
    @Schema(name = "annotation", example = "Новое краткое описание", description = "Краткое описание события",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("annotation")
    String annotation;

    /**
     * id категории к которой относится событие
     */
    @Schema(name = "category", description = "id категории к которой относится событие",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("category")
    Long category;

    /**
     * Полное описание события
     */
    @Schema(name = "description", example = "Новое полное описание", description = "Полное описание события",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("description")
    String description;

    /**
     * Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")
     */
    @Schema(name = "eventDate", example = "2025-01-01 09:08:07",
            description = "Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("eventDate")
    String eventDate;

    /**
     * Get location
     */
    @Valid
    @Schema(name = "location", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("location")
    Location location;

    /**
     * Нужно ли оплачивать участие в событии
     */
    @Schema(name = "paid", example = "false", description = "Нужно ли оплачивать участие в событии",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("paid")
    Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @PositiveOrZero
    @Schema(name = "participantLimit", example = "33",
            description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("participantLimit")
    Integer participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие
     */
    @Schema(name = "requestModeration", example = "false", description = "Нужна ли пре-модерация заявок на участие",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("requestModeration")
    Boolean requestModeration;

    /**
     * Заголовок события
     */
    @Schema(name = "title", example = "Новый заголовок", description = "Заголовок события",
            requiredMode = RequiredMode.NOT_REQUIRED)
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
        AdminUpdateEventRequest adminUpdateEventRequest = (AdminUpdateEventRequest) o;
        return Objects.equals(this.annotation, adminUpdateEventRequest.annotation) &&
                Objects.equals(this.category, adminUpdateEventRequest.category) &&
                Objects.equals(this.description, adminUpdateEventRequest.description) &&
                Objects.equals(this.eventDate, adminUpdateEventRequest.eventDate) &&
                Objects.equals(this.location, adminUpdateEventRequest.location) &&
                Objects.equals(this.paid, adminUpdateEventRequest.paid) &&
                Objects.equals(this.participantLimit, adminUpdateEventRequest.participantLimit) &&
                Objects.equals(this.requestModeration, adminUpdateEventRequest.requestModeration) &&
                Objects.equals(this.title, adminUpdateEventRequest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, location, paid, participantLimit,
                requestModeration, title);
    }
}

