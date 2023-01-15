package ru.practicum.ewm.service;

import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.category.dto.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto update(CategoryDto categoryDto);

    CategoryDto add(NewCategoryDto newCategoryDto);

    void delete(Long catId);
}
