package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.user.dto.UserShortDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Краткая информация о событии
 */
@Schema(name = "EventShortDto", description = "Краткая информация о событии")
@Value
@ToString
public class EventShortDto {
    /**
     * Краткое описание
     */
    @NotNull
    @Schema(
            name = "annotation",
            example = "Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории",
            description = "Краткое описание",
            required = true
    )
    @JsonProperty("annotation")
    String annotation;

    /**
     * Get category
     */
    @NotNull
    @Valid
    @Schema(name = "category", required = true)
    @JsonProperty("category")
    CategoryDto category;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    @Schema(
            name = "confirmedRequests",
            example = "5",
            description = "Количество одобренных заявок на участие в данном событии",
            required = false
    )
    @JsonProperty("confirmedRequests")
    Long confirmedRequests;

    /**
     * Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")
     */
    @NotNull
    @Schema(
            name = "eventDate",
            example = "2024-12-31 15:10:05",
            description = "Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")",
            required = true
    )
    @JsonProperty("eventDate")
    String eventDate;

    /**
     * Идентификатор
     */
    @Schema(name = "id", example = "1", description = "Идентификатор", required = false)
    @JsonProperty("id")
    Long id;

    /**
     * Get initiator
     */
    @NotNull
    @Valid
    @Schema(name = "initiator", required = true)
    @JsonProperty("initiator")
    UserShortDto initiator;

    /**
     * Нужно ли оплачивать участие
     */
    @NotNull
    @Schema(name = "paid", example = "true", description = "Нужно ли оплачивать участие", required = true)
    @JsonProperty("paid")
    Boolean paid;

    /**
     * Заголовок
     */
    @NotNull
    @Schema(name = "title", example = "Знаменитое шоу 'Летающая кукуруза'", description = "Заголовок", required = true)
    @JsonProperty("title")
    String title;

    /**
     * Количество просмотрев события
     */
    @Schema(name = "views", example = "999", description = "Количество просмотрев события", required = false)
    @JsonProperty("views")
    Long views;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventShortDto eventShortDto = (EventShortDto) o;
        return Objects.equals(this.annotation, eventShortDto.annotation) &&
                Objects.equals(this.category, eventShortDto.category) &&
                Objects.equals(this.confirmedRequests, eventShortDto.confirmedRequests) &&
                Objects.equals(this.eventDate, eventShortDto.eventDate) &&
                Objects.equals(this.id, eventShortDto.id) &&
                Objects.equals(this.initiator, eventShortDto.initiator) &&
                Objects.equals(this.paid, eventShortDto.paid) &&
                Objects.equals(this.title, eventShortDto.title) &&
                Objects.equals(this.views, eventShortDto.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, confirmedRequests, eventDate, id, initiator, paid, title, views);
    }
}

