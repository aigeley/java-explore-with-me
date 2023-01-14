package ru.practicum.stats.service;

import ru.practicum.stats.model.EndpointHit;

public interface HitService {
    void hit(EndpointHit endpointHit);
}
