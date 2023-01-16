package ru.practicum.stats.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class EndpointHitMapper extends ElementProjectionMapper<EndpointHitEntity, EndpointHit> {
    public EndpointHitMapper() {
        super(
                EndpointHit.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public EndpointHit toProjection(EndpointHitEntity endpointHitEntity) {
        return endpointHitEntity == null ? null : new EndpointHit(
                endpointHitEntity.getId(),
                endpointHitEntity.getApp(),
                endpointHitEntity.getUri(),
                endpointHitEntity.getIp(),
                endpointHitEntity.getTimestamp().format(DATE_TIME_FORMATTER)
        );
    }

    @Override
    public EndpointHitEntity toElement(EndpointHitEntity endpointHitEntity, EndpointHit endpointHit) {
        endpointHitEntity.setApp(getNullIfBlank(endpointHit.getApp()));
        endpointHitEntity.setUri(getNullIfBlank(endpointHit.getUri()));
        endpointHitEntity.setIp(getNullIfBlank(endpointHit.getIp()));
        Optional.ofNullable(endpointHit.getTimestamp())
                .ifPresent(
                        timestamp -> endpointHitEntity.setTimestamp(LocalDateTime.parse(timestamp, DATE_TIME_FORMATTER))
                );
        return endpointHitEntity;
    }
}
