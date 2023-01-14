package ru.practicum.stats.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementDtoMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;

@Component
public class EndpointHitMapper extends ElementDtoMapper<EndpointHitEntity, EndpointHit> {
    public EndpointHitMapper() {
        super(
                EndpointHit.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public EndpointHit toDto(EndpointHitEntity endpointHitEntity) {
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
        endpointHitEntity.setApp(endpointHit.getApp());
        endpointHitEntity.setUri(endpointHit.getUri());
        endpointHitEntity.setIp(endpointHit.getIp());
        Optional.ofNullable(endpointHit.getTimestamp())
                .ifPresent(
                        timestamp -> endpointHitEntity.setTimestamp(LocalDateTime.parse(timestamp, DATE_TIME_FORMATTER))
                );
        return endpointHitEntity;
    }
}
