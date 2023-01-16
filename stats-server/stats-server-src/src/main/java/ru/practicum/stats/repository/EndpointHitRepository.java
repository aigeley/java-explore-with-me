package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats.model.EndpointHitEntity;

public interface EndpointHitRepository extends JpaRepository<EndpointHitEntity, Long> {
}
