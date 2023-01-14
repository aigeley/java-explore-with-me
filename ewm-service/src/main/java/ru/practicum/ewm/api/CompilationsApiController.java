package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationsService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class CompilationsApiController extends ElementApiController<CompilationsService> implements CompilationsApi {
    protected CompilationsApiController(CompilationsService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        return service.getCompilations(pinned, from, size);
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        return service.getCompilation(compId);
    }
}
