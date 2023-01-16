package ru.practicum.ewm.model.participation.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.practicum.element.model.ElementProjectionMapper;
import ru.practicum.ewm.model.participation.ParticipationRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ParticipationRequestDtoMapper
        extends ElementProjectionMapper<ParticipationRequest, ParticipationRequestDto> {
    public ParticipationRequestDtoMapper() {
        super(
                ParticipationRequestDto.class,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public ParticipationRequestDto toProjection(ParticipationRequest participationRequest) {
        return participationRequest == null ? null : new ParticipationRequestDto(
                participationRequest.getCreated().format(DATE_TIME_FORMATTER),
                participationRequest.getEvent().getId(),
                participationRequest.getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getStatus()
        );
    }

    @Override
    public ParticipationRequest toElement(ParticipationRequest participationRequest,
                                          ParticipationRequestDto participationRequestDto) {
        Optional.ofNullable(participationRequestDto.getCreated())
                .ifPresent(created ->
                        participationRequest.setCreated(LocalDateTime.parse(created, DATE_TIME_FORMATTER)));
        participationRequest.setStatus(participationRequestDto.getStatus());
        return participationRequest;
    }
}
