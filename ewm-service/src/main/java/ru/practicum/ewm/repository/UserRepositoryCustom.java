package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.model.user.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> getUsers(Predicate wherePredicate, Pageable pageable);
}
