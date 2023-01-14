package ru.practicum.element.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.element.model.Element.DATE_TIME_FORMATTER;

@Slf4j
@AllArgsConstructor
public abstract class ErrorHandler {
    protected final ObjectMapper objectMapper;

    public ResponseEntity<String> sendError(Throwable e, HttpStatus httpStatus) throws JsonProcessingException {
        return sendErrorCustomMessage(e, null, httpStatus);
    }

    public ResponseEntity<String> sendErrorCustomMessage(Throwable e, String customMessage, HttpStatus httpStatus)
            throws JsonProcessingException {
        List<String> errors = e instanceof ConstraintViolationException ? ((ConstraintViolationException) e)
                .getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()) : new ArrayList<>();
        String message = (customMessage == null || customMessage.isBlank()) ? e.getMessage() : customMessage;
        ApiError apiError = new ApiError(
                errors,
                message,
                httpStatus.getReasonPhrase(),
                httpStatus,
                LocalDateTime.now().truncatedTo(ChronoUnit.MICROS).format(DATE_TIME_FORMATTER)
        );
        log.info("{} {}: {}", httpStatus.value(), httpStatus.getReasonPhrase(), message, e);
        return new ResponseEntity<>(objectMapper.writeValueAsString(apiError), httpStatus);
    }
}
