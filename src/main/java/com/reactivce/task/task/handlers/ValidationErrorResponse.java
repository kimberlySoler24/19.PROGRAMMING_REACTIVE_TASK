package com.reactivce.task.task.handlers;

import org.springframework.validation.Errors;

public class ValidationErrorResponse extends RuntimeException {
    public ValidationErrorResponse(Errors message) {
        super();
    }
}
