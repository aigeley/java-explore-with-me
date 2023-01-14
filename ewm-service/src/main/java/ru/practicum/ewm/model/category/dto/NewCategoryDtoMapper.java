package ru.practicum.ewm.model.category.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.category.Category;

@Component
public class NewCategoryDtoMapper extends ElementDtoMapper<Category, NewCategoryDto> {
    public NewCategoryDtoMapper() {
        super(
                NewCategoryDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public NewCategoryDto toDto(Category category) {
        return category == null ? null : new NewCategoryDto(
                category.getName()
        );
    }

    @Override
    public Category toElement(Category category, NewCategoryDto newCategoryDto) {
        category.setName(newCategoryDto.getName());
        return category;
    }
}
