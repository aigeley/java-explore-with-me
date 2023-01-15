package ru.practicum.ewm.model.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Пользователь (краткая информация)
 */
@Schema(name = "UserShortDto", description = "Пользователь (краткая информация)")
@Value
@ToString
public class UserShortDto {
    /**
     * Идентификатор
     */
    @NotNull
    @Schema(name = "id", example = "3", description = "Идентификатор", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("id")
    Long id;

    /**
     * Имя
     */
    @NotNull
    @Schema(name = "name", example = "Фёдоров Матвей", description = "Имя", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("name")
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserShortDto userShortDto = (UserShortDto) o;
        return Objects.equals(this.id, userShortDto.id) &&
                Objects.equals(this.name, userShortDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

