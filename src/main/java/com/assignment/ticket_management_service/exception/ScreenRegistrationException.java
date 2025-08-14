package com.assignment.ticket_management_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScreenRegistrationException extends RuntimeException {
    public ScreenRegistrationException(String message) {
        super(message);
    }
}