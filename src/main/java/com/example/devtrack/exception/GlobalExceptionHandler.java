package com.example.devtrack.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Validation error");

        return buildError(HttpStatus.BAD_REQUEST, "Validation error", message, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {

        return buildError(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage(), request);
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {

        return buildError(HttpStatus.UNAUTHORIZED, "Authentication failed", "Invalid email or password", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {

        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", "Something went wrong", request);
    }
}
