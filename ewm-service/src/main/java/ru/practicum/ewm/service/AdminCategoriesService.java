package ru.practicum.ewm.service;

import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.category.dto.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long catId);
}
