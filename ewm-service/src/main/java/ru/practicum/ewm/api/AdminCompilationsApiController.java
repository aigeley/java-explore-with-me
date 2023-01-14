package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.model.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.service.AdminCompilationsService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class AdminCompilationsApiController extends ElementApiController<AdminCompilationsService> implements AdminCompilationsApi {
    protected AdminCompilationsApiController(AdminCompilationsService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        return service.saveCompilation(newCompilationDto);
    }

    @Override
    public void deleteCompilation(Long compId) {
        service.deleteCompilation(compId);
    }

    @Override
    public void removeEventFromCompilation(Long compId, Long eventId) {
        service.removeEventFromCompilation(compId, eventId);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        service.addEventToCompilation(compId, eventId);
    }

    @Override
    public void unpin(Long compId) {
        service.unpin(compId);
    }

    @Override
    public void pin(Long compId) {
        service.pin(compId);
    }
}

