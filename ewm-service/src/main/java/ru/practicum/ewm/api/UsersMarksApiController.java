package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.mark.dto.MarkDto;
import ru.practicum.ewm.service.UsersMarksService;

@AllArgsConstructor
@RestController
public class UsersMarksApiController implements UsersMarksApi {
    private final UsersMarksService service;

    @Override
    public MarkDto add(Long userId, Long eventId, Boolean markValue) {
        return service.add(userId, eventId, markValue);
    }

    @Override
    public MarkDto get(Long userId, Long eventId) {
        return service.get(userId, eventId);
    }
}
