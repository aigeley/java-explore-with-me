package ru.practicum.ewm.model.user.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.user.User;

@Component
public class UserShortDtoMapper extends ElementProjectionMapper<User, UserShortDto> {
    public UserShortDtoMapper() {
        super(
                UserShortDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public UserShortDto toProjection(User user) {
        return user == null ? null : new UserShortDto(
                user.getId(),
                user.getName()
        );
    }

    @Override
    public User toElement(User user, UserShortDto userShortDto) {
        user.setName(userShortDto.getName());
        return user;
    }
}
