package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.UsersEventsRequestsService;

import java.util.List;

@AllArgsConstructor
@RestController
public class UsersEventsRequestsApiController implements UsersEventsRequestsApi {
    private final UsersEventsRequestsService service;

    @Override
    public List<ParticipationRequestDto> getAll(Long userId, Long eventId) {
        return service.getAll(userId, eventId);
    }

    @Override
    public ParticipationRequestDto confirm(Long userId, Long eventId, Long reqId) {
        return service.confirm(userId, eventId, reqId);
    }

    @Override
    public ParticipationRequestDto cancel(Long userId, Long eventId, Long reqId) {
        return service.cancel(userId, eventId, reqId);
    }
}
