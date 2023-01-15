package ru.practicum.ewm.service;

import ru.practicum.ewm.model.category.dto.CategoryDto;

import java.util.List;

public interface CategoriesService {
    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto get(Long catId);
}
