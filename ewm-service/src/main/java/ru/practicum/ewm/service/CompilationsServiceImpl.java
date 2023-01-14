package ru.practicum.ewm.service;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import ru.practicum.element.model.PageRequestFromElement;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.compilation.dto.CompilationDto;
import ru.practicum.ewm.model.compilation.dto.CompilationDtoMapper;
import ru.practicum.ewm.repository.CompilationRepositoryCustom;
import ru.practicum.ewm.service.util.CompilationUtils;

import java.util.List;

import static ru.practicum.ewm.repository.util.QCompilation.compilation;

@AllArgsConstructor
@Service
public class CompilationsServiceImpl implements CompilationsService {
    private final CompilationRepositoryCustom compilationRepositoryCustom;
    private final CompilationDtoMapper compilationDtoMapper;
    private final CompilationUtils compilationUtils;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Predicate wherePredicate;

        if (pinned != null) {
            wherePredicate = compilation.pinned.eq(pinned);
        } else {
            wherePredicate = null;
        }

        PageRequestFromElement pageRequest = PageRequestFromElement.of(from, size, new QSort(compilation.id.asc()));
        List<Compilation> compilations = compilationRepositoryCustom.getCompilations(wherePredicate, pageRequest);
        return compilationDtoMapper.toDtoList(compilationUtils.toCompilationWithEventViewsList(compilations));
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        Compilation compilation = compilationUtils.getAndCheckElement(compId);
        return compilationDtoMapper.toDto(compilationUtils.toCompilationWithEventViews(compilation));
    }
}
