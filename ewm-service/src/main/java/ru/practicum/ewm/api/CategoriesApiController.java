package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.service.CategoriesService;

import java.util.List;

@AllArgsConstructor
@RestController
public class CategoriesApiController implements CategoriesApi {
    private final CategoriesService service;

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        return service.getAll(from, size);
    }

    @Override
    public CategoryDto get(Long catId) {
        return service.get(catId);
    }
}

