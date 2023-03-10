package ru.practicum.stats.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.element.exception.ErrorHandler;

@Order
@Slf4j
@RestControllerAdvice
public class StatsErrorHandler extends ErrorHandler {
    public StatsErrorHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleThrowable(Throwable e) throws JsonProcessingException {
        return sendError(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
