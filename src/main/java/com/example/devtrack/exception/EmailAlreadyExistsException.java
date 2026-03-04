package com.example.devtrack.exception;

import com.example.devtrack.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException() {
        super(
                "Email already exists",
                "There is already a user registered with this email",
                HttpStatus.CONFLICT
        );
    }
}
