package ru.practicum.element.repository;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public abstract class ElementRepositoryAbs<E> extends QuerydslRepositorySupport {
    protected ElementRepositoryAbs(Class<E> elementClass) {
        super(elementClass);
    }

    protected <D> JPQLQuery<D> getPageableQuery(JPQLQuery<D> query, Pageable pageable) {
        return Objects.requireNonNull(super.getQuerydsl()).applyPagination(pageable, query);
    }
}
