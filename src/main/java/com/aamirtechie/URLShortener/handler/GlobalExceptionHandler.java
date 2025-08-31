package com.aamirtechie.URLShortener.handler;

import com.aamirtechie.URLShortener.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String requestId = UUID.randomUUID().toString();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(
                "VALIDATION_FAILED",
                HttpStatus.BAD_REQUEST,
                requestId,
                Instant.now(),
                "One or more fields are invalid",
                errors,
                request.getRequestURI(),
                request.getMethod(),
                "URLShortenerService",
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
        log.error("Validation error occurred: errorResponse: {} errorMsg: {} ", response, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        String requestId = UUID.randomUUID().toString();

        ErrorResponse response = new ErrorResponse(
                "INTERNAL_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR,
                requestId,
                Instant.now(),
                "An unexpected error occurred",
                List.of(),
                request.getRequestURI(),
                request.getMethod(),
                "URLShortenerService",
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
        log.error("An unexpected error occurred:  errorResponse: {} errorMsg: {}", response, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
