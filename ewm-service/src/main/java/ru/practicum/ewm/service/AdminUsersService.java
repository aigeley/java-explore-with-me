package ru.practicum.ewm.service;

import ru.practicum.ewm.model.user.dto.NewUserRequest;
import ru.practicum.ewm.model.user.dto.UserDto;

import java.util.List;

public interface AdminUsersService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto registerUser(NewUserRequest newUserRequest);

    void delete(Long userId);
}
