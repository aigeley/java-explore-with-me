package ru.practicum.ewm.model.category.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.category.Category;

@Component
public class CategoryDtoMapper extends ElementDtoMapper<Category, CategoryDto> {
    public CategoryDtoMapper() {
        super(
                CategoryDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public CategoryDto toDto(Category category) {
        return category == null ? null : new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    @Override
    public Category toElement(Category category, CategoryDto categoryDto) {
        category.setName(categoryDto.getName());
        return category;
    }
}
