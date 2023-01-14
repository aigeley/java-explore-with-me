package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.element.repository.ElementRepositoryAbs;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.repository.util.QCompilation;

import java.util.List;

@Repository
public class CompilationRepositoryCustomImpl extends ElementRepositoryAbs<Compilation> implements CompilationRepositoryCustom {
    public CompilationRepositoryCustomImpl() {
        super(Compilation.class);
    }

    @Override
    public List<Compilation> getCompilations(Predicate wherePredicate, Pageable pageable) {
        JPQLQuery<Compilation> query = from(QCompilation.compilation)
                .select(QCompilation.compilation)
                .where(wherePredicate);
        return getPageableQuery(query, pageable)
                .fetch();
    }
}
