package com.example.reservation_system.web;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception e) {

        log.error("Handle exception", e);
        var errorDto = new ErrorResponseDTO("Internal server error", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFound(EntityNotFoundException e) {
        log.error("Handle EntityNotFoundException exception", e);
        var errorDto = new ErrorResponseDTO("Entity not found", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }


    @ExceptionHandler(exception = {IllegalArgumentException.class, IllegalStateException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(Exception e) {
        log.error("Handle Bad Request exception", e);
        var errorDto = new ErrorResponseDTO("Bad Request", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}
