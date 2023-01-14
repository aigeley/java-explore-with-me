package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.category.dto.CategoryDtoMapper;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.service.util.CategoryUtils;

import java.util.List;

import static ru.practicum.ewm.repository.util.QCategory.category;

@AllArgsConstructor
@Service
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryDtoMapper;
    private final CategoryUtils categoryUtils;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        PageRequestFromElement pageRequest = PageRequestFromElement.of(from, size, new QSort(category.id.asc()));
        return categoryDtoMapper.toDtoList(categoryRepository.findAll(pageRequest).toList());
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        return categoryDtoMapper.toDto(categoryUtils.getAndCheckElement(catId));
    }
}
