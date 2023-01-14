package ru.practicum.stats.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.EndpointHitEntity;
import ru.practicum.stats.model.EndpointHitMapper;
import ru.practicum.stats.repository.EndpointHitRepository;

@Slf4j
@AllArgsConstructor
@Service
public class HitServiceImpl implements HitService {
    private final EndpointHitRepository endpointHitRepository;
    private final EndpointHitMapper endpointHitMapper;

    @Override
    public void hit(EndpointHit endpointHit) {
        EndpointHitEntity endpointHitEntityAdded = endpointHitRepository
                .save(endpointHitMapper.toElement(new EndpointHitEntity(), endpointHit));
        log.info("hit: " + endpointHitEntityAdded);
    }
}
