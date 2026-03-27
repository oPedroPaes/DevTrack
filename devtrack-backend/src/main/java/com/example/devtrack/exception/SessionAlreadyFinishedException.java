package com.example.devtrack.exception;

import com.example.devtrack.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class SessionAlreadyFinishedException extends BusinessException {
    public SessionAlreadyFinishedException() {
        super(
                "Session already finished",
                "This session has already been finished",
                HttpStatus.CONFLICT
        );
    }
}
