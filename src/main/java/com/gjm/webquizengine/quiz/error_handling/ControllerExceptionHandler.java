package com.gjm.webquizengine.quiz.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exc) {
        return new ResponseEntity<>(stringifyValidationErrors(exc), HttpStatus.BAD_REQUEST);
    }

    private String stringifyValidationErrors(MethodArgumentNotValidException exc) {
        StringBuilder stringBuilder = new StringBuilder();

        for(var error : exc.getBindingResult().getFieldErrors()) {
            stringBuilder.append(error.getField());
            stringBuilder.append(": ");
            stringBuilder.append(error.getDefaultMessage());
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
