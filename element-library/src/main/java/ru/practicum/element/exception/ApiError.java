package ru.practicum.element.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * Сведения об ошибке
 */
@Schema(name = "ApiError", description = "Сведения об ошибке")
@Value
@ToString
public class ApiError {

    /**
     * Список стектрейсов или описания ошибок
     */
    @Schema(name = "errors", example = "[]", description = "Список стектрейсов или описания ошибок", required = false)
    @JsonProperty("errors")
    @Valid
    List<String> errors;

    /**
     * Сообщение об ошибке
     */
    @Schema(name = "message", example = "Only pending or canceled events can be changed",
            description = "Сообщение об ошибке", required = false)
    @JsonProperty("message")
    String message;

    /**
     * Общее описание причины ошибки
     */
    @Schema(name = "reason", example = "For the requested operation the conditions are not met.",
            description = "Общее описание причины ошибки", required = false)
    @JsonProperty("reason")
    String reason;

    /**
     * Код статуса HTTP-ответа
     */
    @Schema(name = "status", example = "FORBIDDEN", description = "Код статуса HTTP-ответа", required = false)
    @JsonProperty("status")
    HttpStatus status;

    /**
     * Дата и время когда произошла ошибка (в формате \"yyyy-MM-dd HH:mm:ss\")
     */
    @Schema(name = "timestamp", example = "2022-06-09 06:27:23",
            description = "Дата и время когда произошла ошибка (в формате \"yyyy-MM-dd HH:mm:ss\")", required = false)
    @JsonProperty("timestamp")
    String timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiError apiError = (ApiError) o;
        return Objects.equals(this.errors, apiError.errors) &&
                Objects.equals(this.message, apiError.message) &&
                Objects.equals(this.reason, apiError.reason) &&
                Objects.equals(this.status, apiError.status) &&
                Objects.equals(this.timestamp, apiError.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors, message, reason, status, timestamp);
    }
}

