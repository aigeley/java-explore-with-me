package ru.practicum.ewm.exception.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.element.exception.ErrorHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class EventErrorHandler extends ErrorHandler {
    public EventErrorHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEventCouldNotBePublishedException(EventStateIncorrectException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.FORBIDDEN);
    }
}
