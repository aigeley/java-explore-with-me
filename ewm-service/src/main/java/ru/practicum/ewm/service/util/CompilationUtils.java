package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.repository.CompilationRepository;

@Component
public class CompilationUtils extends ElementUtils<Compilation> {
    public CompilationUtils(CompilationRepository compilationRepository) {
        super(Compilation.ELEMENT_NAME, compilationRepository);
    }
}
