package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.UserRepository;

@Component
public class UserUtils extends ElementUtils<User> {
    public UserUtils(UserRepository userRepository) {
        super(User.ELEMENT_NAME, userRepository);
    }
}
