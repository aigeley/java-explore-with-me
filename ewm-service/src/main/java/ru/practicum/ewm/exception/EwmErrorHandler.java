package ru.practicum.ewm.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.element.exception.ErrorHandler;

import javax.validation.ConstraintViolationException;

@Order
@Slf4j
@RestControllerAdvice
public class EwmErrorHandler extends ErrorHandler {
    public EwmErrorHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) throws JsonProcessingException {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e)
            throws JsonProcessingException {
        return sendError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleThrowable(Throwable e) throws JsonProcessingException {
        return sendError(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
