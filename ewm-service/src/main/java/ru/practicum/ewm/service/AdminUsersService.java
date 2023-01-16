package ru.practicum.ewm.service;

import ru.practicum.ewm.model.user.dto.NewUserRequest;
import ru.practicum.ewm.model.user.dto.UserDto;

import java.util.List;

public interface AdminUsersService {
    List<UserDto> getAll(List<Long> ids, Integer from, Integer size);

    UserDto register(NewUserRequest newUserRequest);

    void delete(Long userId);
}
