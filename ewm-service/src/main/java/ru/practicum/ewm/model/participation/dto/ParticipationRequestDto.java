package ru.practicum.ewm.model.participation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;
import ru.practicum.ewm.model.participation.StatusEnum;

import java.util.Objects;

/**
 * Заявка на участие в событии
 */
@Schema(name = "ParticipationRequestDto", description = "Заявка на участие в событии")
@Value
@ToString
public class ParticipationRequestDto {
    /**
     * Дата и время создания заявки
     */
    @Schema(name = "created", example = "2022-09-06T21:10:05.432", description = "Дата и время создания заявки",
            required = false)
    @JsonProperty("created")
    String created;

    /**
     * Идентификатор события
     */
    @Schema(name = "event", example = "1", description = "Идентификатор события", required = false)
    @JsonProperty("event")
    Long event;

    /**
     * Идентификатор заявки
     */
    @Schema(name = "id", example = "3", description = "Идентификатор заявки", required = false)
    @JsonProperty("id")
    Long id;

    /**
     * Идентификатор пользователя, отправившего заявку
     */
    @Schema(name = "requester", example = "2", description = "Идентификатор пользователя, отправившего заявку",
            required = false)
    @JsonProperty("requester")
    Long requester;

    /**
     * Статус заявки
     */
    @Schema(name = "status", example = "PENDING", description = "Статус заявки", required = false)
    @JsonProperty("status")
    StatusEnum status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipationRequestDto participationRequestDto = (ParticipationRequestDto) o;
        return Objects.equals(this.created, participationRequestDto.created) &&
                Objects.equals(this.event, participationRequestDto.event) &&
                Objects.equals(this.id, participationRequestDto.id) &&
                Objects.equals(this.requester, participationRequestDto.requester) &&
                Objects.equals(this.status, participationRequestDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, event, id, requester, status);
    }
}

