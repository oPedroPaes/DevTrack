package com.example.devtrack.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    @Getter
    private final String error;

    public BusinessException(
            String error,
            String message,
            HttpStatus status
    ) {
        super(message);
        this.error = error;
        this.status = status;
    }

}
