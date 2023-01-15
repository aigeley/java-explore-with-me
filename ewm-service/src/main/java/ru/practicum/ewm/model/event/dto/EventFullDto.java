package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.user.dto.UserShortDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * EventFullDto
 */
@Value
@ToString
public class EventFullDto {
    /**
     * Краткое описание
     */
    @NotNull
    @Schema(name = "annotation",
            example = "Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории",
            description = "Краткое описание", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("annotation")
    String annotation;

    /**
     * Get category
     */
    @NotNull
    @Valid
    @Schema(name = "category", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("category")
    CategoryDto category;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    @Schema(name = "confirmedRequests", example = "5",
            description = "Количество одобренных заявок на участие в данном событии",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("confirmedRequests")
    Long confirmedRequests;

    /**
     * Дата и время создания события (в формате \"yyyy-MM-dd HH:mm:ss\")
     */
    @Schema(name = "createdOn", example = "2022-09-06 11:00:23",
            description = "Дата и время создания события (в формате \"yyyy-MM-dd HH:mm:ss\")",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("createdOn")
    String createdOn;

    /**
     * Полное описание события
     */
    @Schema(name = "description",
            example = "Что получится, если соединить кукурузу и полёт? Создатели \"Шоу летающей кукурузы\" " +
                    "испытали эту идею на практике и воплотили в жизнь инновационный проект, " +
                    "предлагающий свежий взгляд на развлечения...", description = "Полное описание события",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("description")
    String description;

    /**
     * Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")
     */
    @NotNull
    @Schema(name = "eventDate", example = "2024-12-31 15:10:05",
            description = "Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")",
            requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("eventDate")
    String eventDate;

    /**
     * Идентификатор
     */
    @Schema(name = "id", example = "1", description = "Идентификатор", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    Long id;

    /**
     * Get initiator
     */
    @NotNull
    @Valid
    @Schema(name = "initiator", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("initiator")
    UserShortDto initiator;

    /**
     * Get location
     */
    @NotNull
    @Valid
    @Schema(name = "location", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("location")
    Location location;

    /**
     * Нужно ли оплачивать участие
     */
    @NotNull
    @Schema(name = "paid", example = "true", description = "Нужно ли оплачивать участие",
            requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("paid")
    Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Schema(name = "participantLimit", example = "10",
            description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("participantLimit")
    Integer participantLimit;

    /**
     * Дата и время публикации события (в формате \"yyyy-MM-dd HH:mm:ss\")
     */
    @Schema(name = "publishedOn", example = "2022-09-06 15:10:05",
            description = "Дата и время публикации события (в формате \"yyyy-MM-dd HH:mm:ss\")",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("publishedOn")
    String publishedOn;

    /**
     * Нужна ли пре-модерация заявок на участие
     */
    @Schema(name = "requestModeration", example = "true", description = "Нужна ли пре-модерация заявок на участие",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("requestModeration")
    Boolean requestModeration;

    /**
     * Список состояний жизненного цикла события
     */
    @Schema(name = "state", example = "PUBLISHED", description = "Список состояний жизненного цикла события",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("state")
    StateEnum state;

    /**
     * Заголовок
     */
    @NotNull
    @Schema(name = "title", example = "Знаменитое шоу 'Летающая кукуруза'", description = "Заголовок",
            requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("title")
    String title;

    /**
     * Количество просмотрев события
     */
    @Schema(name = "views", example = "999", description = "Количество просмотрев события",
            requiredMode = RequiredMode.NOT_REQUIRED)
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
        EventFullDto eventFullDto = (EventFullDto) o;
        return Objects.equals(this.annotation, eventFullDto.annotation) &&
                Objects.equals(this.category, eventFullDto.category) &&
                Objects.equals(this.confirmedRequests, eventFullDto.confirmedRequests) &&
                Objects.equals(this.createdOn, eventFullDto.createdOn) &&
                Objects.equals(this.description, eventFullDto.description) &&
                Objects.equals(this.eventDate, eventFullDto.eventDate) &&
                Objects.equals(this.id, eventFullDto.id) &&
                Objects.equals(this.initiator, eventFullDto.initiator) &&
                Objects.equals(this.location, eventFullDto.location) &&
                Objects.equals(this.paid, eventFullDto.paid) &&
                Objects.equals(this.participantLimit, eventFullDto.participantLimit) &&
                Objects.equals(this.publishedOn, eventFullDto.publishedOn) &&
                Objects.equals(this.requestModeration, eventFullDto.requestModeration) &&
                Objects.equals(this.state, eventFullDto.state) &&
                Objects.equals(this.title, eventFullDto.title) &&
                Objects.equals(this.views, eventFullDto.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, confirmedRequests, createdOn, description, eventDate, id, initiator,
                location, paid, participantLimit, publishedOn, requestModeration, state, title, views);
    }
}

