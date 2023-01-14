package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.element.repository.ElementRepositoryAbs;
import ru.practicum.ewm.model.user.User;

import java.util.List;

import static ru.practicum.ewm.repository.util.QUser.user;

@Repository
public class UserRepositoryCustomImpl extends ElementRepositoryAbs<User> implements UserRepositoryCustom {
    public UserRepositoryCustomImpl() {
        super(User.class);
    }

    @Override
    public List<User> getUsers(Predicate wherePredicate, Pageable pageable) {
        JPQLQuery<User> query = from(user)
                .select(user)
                .where(wherePredicate);
        return getPageableQuery(query, pageable)
                .fetch();
    }
}
