package ru.practicum.ewm.service;

import ru.practicum.ewm.model.category.dto.CategoryDto;

import java.util.List;

public interface CategoriesService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Long catId);
}
