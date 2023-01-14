package ru.practicum.stats.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.service.HitService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class HitApiController extends ElementApiController<HitService> implements HitApi {
    public HitApiController(HitService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public void hit(EndpointHit endpointHit) {
        service.hit(endpointHit);
    }
}
