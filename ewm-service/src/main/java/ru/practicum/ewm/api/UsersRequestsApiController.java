package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.UsersRequestsService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class UsersRequestsApiController extends ElementApiController<UsersRequestsService> implements UsersRequestsApi {
    protected UsersRequestsApiController(UsersRequestsService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        return service.getUserRequests(userId);
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        return service.addParticipationRequest(userId, eventId);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return service.cancelRequest(userId, requestId);
    }
}
