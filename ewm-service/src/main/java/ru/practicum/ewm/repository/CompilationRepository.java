package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.compilation.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
