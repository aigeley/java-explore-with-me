package ru.practicum.ewm.model.event.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.practicum.element.model.ElementProjectionMapper.DATE_TIME_FORMATTER;

public class EventDateValidator implements ConstraintValidator<EventDate, String> {
    public static final int VALID_HOURS = 2;
    public static final String VALID_HOURS_NAME = "часа";

    @Override
    public boolean isValid(String eventDate, ConstraintValidatorContext context) {
        if (eventDate == null) {
            return true;
        }

        return LocalDateTime.parse(eventDate, DATE_TIME_FORMATTER)
                .isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).plusHours(VALID_HOURS));
    }

}