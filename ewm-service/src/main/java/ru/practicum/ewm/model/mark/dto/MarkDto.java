package ru.practicum.ewm.model.mark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import java.util.Objects;

/**
 * Оценка
 */
@Schema(name = "MarkDto", description = "Оценка")
@Value
@ToString
public class MarkDto {
    /**
     * Идентификатор оценки
     */
    @Schema(name = "id", example = "3", description = "Идентификатор оценки", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    Long id;

    /**
     * Дата и время создания оценки
     */
    @Schema(name = "created", example = "2022-09-06 21:10:05.432", description = "Дата и время создания оценки",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("created")
    String created;

    /**
     * Идентификатор события
     */
    @Schema(name = "eventId", example = "1", description = "Идентификатор события",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("eventId")
    Long eventId;

    /**
     * Идентификатор пользователя, оставившего оценку
     */
    @Schema(name = "userId", example = "2", description = "Идентификатор пользователя, оставившего оценку",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("userId")
    Long userId;

    /**
     * Значение оценки:
     * 1 - лайк,
     * 0 - дизлайк
     */
    @Schema(name = "markValue", example = "1", description = "Значение оценки: 1 - лайк, 0 - дизлайк",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("markValue")
    Integer markValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarkDto markDto = (MarkDto) o;
        return Objects.equals(id, markDto.id) && Objects.equals(created, markDto.created) &&
                Objects.equals(eventId, markDto.eventId) && Objects.equals(userId, markDto.userId) &&
                Objects.equals(markValue, markDto.markValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, eventId, userId, markValue);
    }
}

