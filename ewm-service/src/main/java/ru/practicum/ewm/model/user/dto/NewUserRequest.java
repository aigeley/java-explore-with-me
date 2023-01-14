package ru.practicum.ewm.model.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Данные нового пользователя
 */
@Schema(name = "NewUserRequest", description = "Данные нового пользователя")
@Value
@ToString
public class NewUserRequest {
    /**
     * Почтовый адрес
     */
    @Email
    @NotBlank
    @Schema(name = "email", example = "ivan.petrov@practicummail.ru", description = "Почтовый адрес", required = true)
    @JsonProperty("email")
    String email;

    /**
     * Имя
     */
    @NotBlank
    @Schema(name = "name", example = "Иван Петров", description = "Имя", required = true)
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
        NewUserRequest newUserRequest = (NewUserRequest) o;
        return Objects.equals(this.email, newUserRequest.email) &&
                Objects.equals(this.name, newUserRequest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }
}

