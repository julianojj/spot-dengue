package com.example.spotdengue.infra.api.middlewares;

import com.example.spotdengue.core.exceptions.NotFoundException;
import com.example.spotdengue.core.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> NotFoundExceptionHandler(NotFoundException err) {
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler(err.getMessage(), 404);
        return new ResponseEntity<>(responseErrorHandler, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> ValidationExceptionHandler(ValidationException err) {
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler(err.getMessage(), 422);
        return new ResponseEntity<>(responseErrorHandler, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> ExceptionHandler(Exception err) {
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler(err.getMessage(), 500);
        return new ResponseEntity<>(responseErrorHandler, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ResponseErrorHandler(String message, Integer code) { }
}
