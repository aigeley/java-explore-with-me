package ru.practicum.ewm.model.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
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
            required = false)
    @JsonProperty("events")
    @Valid
    @JsonDeserialize(as = LinkedHashSet.class)
    Set<Long> events;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    @Schema(name = "pinned", example = "false", description = "Закреплена ли подборка на главной странице сайта",
            required = false)
    @JsonProperty(value = "pinned", defaultValue = "false")
    Boolean pinned;

    /**
     * Заголовок подборки
     */
    @NotBlank
    @Schema(name = "title", example = "Летние концерты", description = "Заголовок подборки", required = true)
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

