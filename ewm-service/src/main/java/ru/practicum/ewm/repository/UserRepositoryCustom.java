package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.model.user.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> getAll(Predicate wherePredicate, Pageable pageable);
}
