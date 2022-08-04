package com.tarasenko.universityapp.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ExceptionInfo> exceptionHandler(IncorrectDataException exception) {
        ExceptionInfo info = new ExceptionInfo(exception.getMessage());

        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInfo> exceptionHandler(Exception exception) {
        ExceptionInfo info = new ExceptionInfo(exception.getMessage());

        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
