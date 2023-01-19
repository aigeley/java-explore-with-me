package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.service.UsersRecommendationsService;

import java.util.List;

@AllArgsConstructor
@RestController
public class UsersRecommendationsApiController implements UsersRecommendationsApi {
    private final UsersRecommendationsService service;

    @Override
    public List<EventShortDto> getAll(Long userId) {
        return service.getAll(userId);
    }
}
