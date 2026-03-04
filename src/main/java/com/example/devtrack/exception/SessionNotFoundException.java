package com.example.devtrack.exception;

import com.example.devtrack.exception.base.NotFoundException;

public class SessionNotFoundException extends NotFoundException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
