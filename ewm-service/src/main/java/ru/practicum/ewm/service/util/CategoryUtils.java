package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.repository.CategoryRepository;

@Component
public class CategoryUtils extends ElementUtils<Category> {
    public CategoryUtils(CategoryRepository categoryRepository) {
        super(Category.ELEMENT_NAME, categoryRepository);
    }
}
