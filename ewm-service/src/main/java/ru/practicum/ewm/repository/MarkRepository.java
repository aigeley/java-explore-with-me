package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.mark.Mark;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    Mark findByUserIdAndEventId(Long userId, Long eventId);
}
