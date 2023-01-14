package ru.practicum.ewm.service;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.model.user.dto.NewUserRequest;
import ru.practicum.ewm.model.user.dto.NewUserRequestMapper;
import ru.practicum.ewm.model.user.dto.UserDto;
import ru.practicum.ewm.model.user.dto.UserDtoMapper;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.repository.UserRepositoryCustom;
import ru.practicum.ewm.service.util.UserUtils;

import java.util.List;

import static ru.practicum.ewm.repository.util.QUser.user;

@AllArgsConstructor
@Slf4j
@Service
public class AdminUsersServiceImpl implements AdminUsersService {
    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final UserUtils userUtils;
    private final NewUserRequestMapper newUserRequestMapper;
    private final UserDtoMapper userDtoMapper;

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Predicate wherePredicate;

        if (ids != null && !ids.isEmpty()) {
            wherePredicate = user.id.in(ids);
        } else {
            wherePredicate = null;
        }

        PageRequestFromElement pageRequest = PageRequestFromElement.of(from, size, new QSort(user.id.asc()));
        return userDtoMapper.toDtoList(userRepositoryCustom.getUsers(wherePredicate, pageRequest));
    }

    @Override
    public UserDto registerUser(NewUserRequest newUserRequest) {
        User user = newUserRequestMapper.toElement(new User(), newUserRequest);
        User userRegistered = userRepository.save(user);
        log.info("registerUser: " + userRegistered);
        return userDtoMapper.toDto(userRegistered);
    }

    @Override
    public void delete(Long userId) {
        User user = userUtils.getAndCheckElement(userId);
        userRepository.delete(user);
        log.info("delete: " + user);
    }
}
