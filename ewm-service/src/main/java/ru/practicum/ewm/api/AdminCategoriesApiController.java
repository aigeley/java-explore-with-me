package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.category.dto.NewCategoryDto;
import ru.practicum.ewm.service.AdminCategoriesService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class AdminCategoriesApiController extends ElementApiController<AdminCategoriesService> implements AdminCategoriesApi {
    protected AdminCategoriesApiController(AdminCategoriesService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return service.updateCategory(categoryDto);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return service.addCategory(newCategoryDto);
    }

    @Override
    public void deleteCategory(Long catId) {
        service.deleteCategory(catId);
    }
}

