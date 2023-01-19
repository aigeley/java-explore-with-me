package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.event.EventCheck;
import ru.practicum.ewm.exception.participation.ParticipationRequestCheck;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.mark.Mark;
import ru.practicum.ewm.model.mark.dto.MarkDto;
import ru.practicum.ewm.model.mark.dto.MarkDtoMapper;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;
import ru.practicum.ewm.repository.MarkRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.util.EventUtils;
import ru.practicum.ewm.service.util.UserUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Slf4j
@Service
public class UsersMarksServiceImpl implements UsersMarksService {
    private final MarkRepository markRepository;
    private final MarkDtoMapper markDtoMapper;
    private final ParticipationRequestRepository participationRequestRepository;
    private final UserUtils userUtils;
    private final EventUtils eventUtils;

    @Transactional
    @Override
    public MarkDto add(Long userId, Long eventId, Integer markValue) {
        userUtils.getAndCheck(userId);

        Event event = eventUtils.getAndCheck(eventId);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        EventCheck.eventDateNotPassYet(event, now);

        ParticipationRequest request =
                participationRequestRepository.findByRequester_IdAndEvent_IdAndStatus(userId, eventId,
                        StatusEnum.CONFIRMED);
        ParticipationRequestCheck.userHasBeenOnEvent(request, userId, eventId);

        Mark mark = new Mark();
        mark.setCreated(now);
        mark.setEventId(eventId);
        mark.setUserId(userId);
        mark.setMarkValue(markValue);
        Mark markAdded = markRepository.save(mark);
        log.info("addMark: " + markAdded);
        return markDtoMapper.toProjection(markAdded);
    }

    @Override
    public MarkDto get(Long userId, Long eventId) {
        userUtils.getAndCheck(userId);
        eventUtils.getAndCheck(eventId);
        return markDtoMapper.toProjection(markRepository.findByUserIdAndEventId(userId, eventId));
    }
}
