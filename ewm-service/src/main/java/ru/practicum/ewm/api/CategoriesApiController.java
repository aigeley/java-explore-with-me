package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.service.CategoriesService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class CategoriesApiController extends ElementApiController<CategoriesService> implements CategoriesApi {
    protected CategoriesApiController(CategoriesService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return service.getCategories(from, size);
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        return service.getCategory(catId);
    }
}

