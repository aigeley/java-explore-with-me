package ru.practicum.ewm.model.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Пользователь
 */
@Schema(name = "UserDto", description = "Пользователь")
@Value
@ToString
public class UserDto {
    /**
     * Почтовый адрес
     */
    @NotNull
    @Schema(name = "email", example = "petrov.i@practicummail.ru", description = "Почтовый адрес",
            requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("email")
    String email;

    /**
     * Идентификатор
     */
    @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "Идентификатор",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    Long id;

    /**
     * Имя
     */
    @NotNull
    @Schema(name = "name", example = "Петров Иван", description = "Имя", requiredMode = RequiredMode.REQUIRED)
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
        UserDto userDto = (UserDto) o;
        return Objects.equals(this.email, userDto.email) &&
                Objects.equals(this.id, userDto.id) &&
                Objects.equals(this.name, userDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id, name);
    }
}

