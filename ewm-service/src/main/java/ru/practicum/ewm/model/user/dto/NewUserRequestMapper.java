package ru.practicum.ewm.model.user.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.user.User;

@Component
public class NewUserRequestMapper extends ElementProjectionMapper<User, NewUserRequest> {
    public NewUserRequestMapper() {
        super(
                NewUserRequest.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public NewUserRequest toProjection(User user) {
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
