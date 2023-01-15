package ru.practicum.ewm.model.event.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static ru.practicum.ewm.model.event.dto.validation.EventDateValidator.VALID_HOURS;
import static ru.practicum.ewm.model.event.dto.validation.EventDateValidator.VALID_HOURS_NAME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
@Documented
public @interface EventDate {

    String message() default "дата и время на которые намечено событие не может быть раньше, чем через " +
            VALID_HOURS + " " + VALID_HOURS_NAME + " от текущего момента";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}