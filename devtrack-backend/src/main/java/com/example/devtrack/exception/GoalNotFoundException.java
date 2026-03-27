package com.example.devtrack.exception;

import com.example.devtrack.exception.base.NotFoundException;

public class GoalNotFoundException extends NotFoundException {
    public GoalNotFoundException(String message) {
        super(message);
    }
}
