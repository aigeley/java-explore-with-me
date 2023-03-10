package ru.practicum.ewm.exception.participation;

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
public class ParticipationRequestErrorHandler extends ErrorHandler {
    public ParticipationRequestErrorHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleParticipationLimitIsReachedException(ParticipationLimitIsReachedException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleParticipationForbiddenException(ParticipationForbiddenException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleParticipationRequestorIsDifferentException(
            ParticipationRequestorIsDifferentException e) throws JsonProcessingException {
        return sendError(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleParticipationNotConfirmedException(ParticipationNotConfirmedException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.FORBIDDEN);
    }
}
