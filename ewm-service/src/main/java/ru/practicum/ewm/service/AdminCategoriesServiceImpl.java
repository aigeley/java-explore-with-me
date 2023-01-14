package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.category.dto.CategoryDtoMapper;
import ru.practicum.ewm.model.category.dto.NewCategoryDto;
import ru.practicum.ewm.model.category.dto.NewCategoryDtoMapper;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.service.util.CategoryUtils;

@AllArgsConstructor
@Slf4j
@Service
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    private final CategoryRepository categoryRepository;
    private final CategoryUtils categoryUtils;
    private final CategoryDtoMapper categoryDtoMapper;
    private final NewCategoryDtoMapper newCategoryDtoMapper;

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryUtils.getAndCheckElement(categoryDto.getId());
        Category categoryToUpdate = categoryDtoMapper.toElement(category, categoryDto);
        Category categoryUpdated = categoryRepository.save(categoryToUpdate);
        log.info("updateCategory: " + categoryUpdated);
        return categoryDtoMapper.toDto(categoryUpdated);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = newCategoryDtoMapper.toElement(new Category(), newCategoryDto);
        Category categoryAdded = categoryRepository.save(category);
        log.info("addCategory: " + categoryAdded);
        return categoryDtoMapper.toDto(categoryAdded);
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = categoryUtils.getAndCheckElement(catId);
        categoryRepository.delete(category);
        log.info("deleteCategory: " + category);
    }
}
