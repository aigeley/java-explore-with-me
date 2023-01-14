package ru.practicum.ewm.model.user.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.user.User;

@Component
public class UserDtoMapper extends ElementDtoMapper<User, UserDto> {
    public UserDtoMapper() {
        super(
                UserDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public UserDto toDto(User user) {
        return user == null ? null : new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName()
        );
    }

    @Override
    public User toElement(User user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        return user;
    }
}
