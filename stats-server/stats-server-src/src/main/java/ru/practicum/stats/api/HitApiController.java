package ru.practicum.stats.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.service.HitService;

@AllArgsConstructor
@RestController
public class HitApiController implements HitApi {
    private final HitService service;

    @Override
    public void add(EndpointHit endpointHit) {
        service.add(endpointHit);
    }
}
