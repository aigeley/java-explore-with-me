package ru.practicum.stats.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.stats.model.ViewStats;
import ru.practicum.stats.service.StatsService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class StatsApiController extends ElementApiController<StatsService> implements StatsApi {
    public StatsApiController(StatsService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}
