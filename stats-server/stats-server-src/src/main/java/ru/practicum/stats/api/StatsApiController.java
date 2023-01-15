package ru.practicum.stats.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.stats.model.ViewStats;
import ru.practicum.stats.service.StatsService;

import java.util.List;

@AllArgsConstructor
@RestController
public class StatsApiController implements StatsApi {
    private final StatsService service;

    @Override
    public List<ViewStats> getAll(String start, String end, List<String> uris, Boolean unique) {
        return service.getAll(start, end, uris, unique);
    }
}
