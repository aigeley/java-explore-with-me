package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.category.dto.NewCategoryDto;
import ru.practicum.ewm.service.AdminCategoriesService;

@AllArgsConstructor
@RestController
public class AdminCategoriesApiController implements AdminCategoriesApi {
    private final AdminCategoriesService service;

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        return service.update(categoryDto);
    }

    @Override
    public CategoryDto add(NewCategoryDto newCategoryDto) {
        return service.add(newCategoryDto);
    }

    @Override
    public void delete(Long catId) {
        service.delete(catId);
    }
}

