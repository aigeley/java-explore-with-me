package ru.practicum.ewm.model.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;
import ru.practicum.ewm.model.event.dto.EventShortDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Подборка событий
 */
@Schema(name = "CompilationDto", description = "Подборка событий")
@Value
@ToString
public class CompilationDto {
    /**
     * Список событий входящих в подборку
     */
    @Schema(name = "events", description = "Список событий входящих в подборку", required = false)
    @JsonProperty("events")
    @Valid
    @JsonDeserialize(as = LinkedHashSet.class)
    Set<EventShortDto> events;

    /**
     * Идентификатор
     */
    @NotNull
    @Schema(name = "id", example = "1", description = "Идентификатор", required = true)
    @JsonProperty("id")
    Long id;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    @NotNull
    @Schema(name = "pinned", example = "true", description = "Закреплена ли подборка на главной странице сайта",
            required = true)
    @JsonProperty("pinned")
    Boolean pinned;

    /**
     * Заголовок подборки
     */
    @NotNull
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
        CompilationDto compilationDto = (CompilationDto) o;
        return Objects.equals(this.events, compilationDto.events) &&
                Objects.equals(this.id, compilationDto.id) &&
                Objects.equals(this.pinned, compilationDto.pinned) &&
                Objects.equals(this.title, compilationDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events, id, pinned, title);
    }
}

