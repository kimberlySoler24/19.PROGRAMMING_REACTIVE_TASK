package com.reactivce.task.task.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TaskNotFoundException.class})
    public Mono<ResponseEntity<String>> handlerNotFoundException(TaskNotFoundException ex){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }
    @ExceptionHandler({ValidationException.class})
    public Mono<ResponseEntity<ValidationErrorResponse>> handlerTaskValidationException(ValidationException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse(ex.getErrors());
        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }
}
