package ru.practicum.ewm.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.model.user.dto.NewUserRequest;
import ru.practicum.ewm.model.user.dto.UserDto;
import ru.practicum.ewm.service.AdminUsersService;

import java.util.List;

@AllArgsConstructor
@RestController
public class AdminUsersApiController implements AdminUsersApi {
    private final AdminUsersService service;

    @Override
    public List<UserDto> getAll(List<Long> ids, Integer from, Integer size) {
        return service.getAll(ids, from, size);
    }

    @Override
    public UserDto register(NewUserRequest newUserRequest) {
        return service.register(newUserRequest);
    }

    @Override
    public void delete(Long userId) {
        service.delete(userId);
    }
}

