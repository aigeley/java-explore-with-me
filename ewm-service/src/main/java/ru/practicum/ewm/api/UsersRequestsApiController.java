package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.UsersRequestsService;

import java.util.List;

@AllArgsConstructor
@RestController
public class UsersRequestsApiController implements UsersRequestsApi {
    private final UsersRequestsService service;

    @Override
    public List<ParticipationRequestDto> getAll(Long userId) {
        return service.getAll(userId);
    }

    @Override
    public ParticipationRequestDto add(Long userId, Long eventId) {
        return service.add(userId, eventId);
    }

    @Override
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        return service.cancel(userId, requestId);
    }
}
