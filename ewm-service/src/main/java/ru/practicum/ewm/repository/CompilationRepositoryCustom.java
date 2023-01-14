package ru.practicum.ewm.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.model.compilation.Compilation;

import java.util.List;

public interface CompilationRepositoryCustom {
    List<Compilation> getCompilations(Predicate wherePredicate, Pageable pageable);
}
