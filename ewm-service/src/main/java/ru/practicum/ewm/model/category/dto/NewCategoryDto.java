package ru.practicum.ewm.model.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Данные для добавления новой категории
 */
@Schema(name = "NewCategoryDto", description = "Данные для добавления новой категории")
@Value
@ToString
public class NewCategoryDto {
    /**
     * Название категории
     */
    @NotBlank
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
        NewCategoryDto newCategoryDto = (NewCategoryDto) o;
        return Objects.equals(this.name, newCategoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

