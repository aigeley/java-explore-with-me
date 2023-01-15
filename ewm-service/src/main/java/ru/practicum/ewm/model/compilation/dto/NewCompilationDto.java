package ru.practicum.ewm.model.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Подборка событий
 */
@Schema(name = "NewCompilationDto", description = "Подборка событий")
@Value
@ToString
public class NewCompilationDto {
    /**
     * Список идентификаторов событий входящих в подборку
     */
    @Schema(name = "events", example = "[1,2,3]", description = "Список идентификаторов событий входящих в подборку",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("events")
    @Valid
    @JsonDeserialize(as = LinkedHashSet.class)
    Set<Long> events;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    @Schema(name = "pinned", example = "false", defaultValue = "false",
            description = "Закреплена ли подборка на главной странице сайта", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty(value = "pinned")
    Boolean pinned;

    /**
     * Заголовок подборки
     */
    @NotBlank
    @Schema(name = "title", example = "Летние концерты", description = "Заголовок подборки",
            requiredMode = RequiredMode.REQUIRED)
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
        NewCompilationDto newCompilationDto = (NewCompilationDto) o;
        return Objects.equals(this.events, newCompilationDto.events) &&
                Objects.equals(this.pinned, newCompilationDto.pinned) &&
                Objects.equals(this.title, newCompilationDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events, pinned, title);
    }
}

