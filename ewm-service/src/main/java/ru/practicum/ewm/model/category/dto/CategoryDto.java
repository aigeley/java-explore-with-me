package ru.practicum.ewm.model.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Категория
 */
@Schema(name = "CategoryDto", description = "Категория")
@Value
@ToString
public class CategoryDto {
    /**
     * Идентификатор категории
     */
    @NotNull
    @Schema(name = "id", example = "1", description = "Идентификатор категории", required = true)
    @JsonProperty("id")
    Long id;

    /**
     * Название категории
     */
    @NotNull
    @Schema(name = "name", example = "Концерты", description = "Название категории", required = true)
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
        CategoryDto categoryDto = (CategoryDto) o;
        return Objects.equals(this.id, categoryDto.id) &&
                Objects.equals(this.name, categoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

