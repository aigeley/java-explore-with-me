package ru.practicum.ewm.model.user.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;
import ru.practicum.ewm.model.user.User;

@Component
public class NewUserRequestMapper extends ElementDtoMapper<User, NewUserRequest> {
    public NewUserRequestMapper() {
        super(
                NewUserRequest.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public NewUserRequest toDto(User user) {
        return user == null ? null : new NewUserRequest(
                user.getEmail(),
                user.getName()
        );
    }

    @Override
    public User toElement(User user, NewUserRequest newUserRequest) {
        user.setEmail(newUserRequest.getEmail());
        user.setName(newUserRequest.getName());
        return user;
    }
}
