package com.gjm.webquizengine.quiz.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exc) {
        return new ResponseEntity<>(
                stringifyValidationErrors(exc.getBindingResult().getFieldErrors()),
                HttpStatus.BAD_REQUEST
        );
    }

    private String stringifyValidationErrors(List<FieldError> errors) {
        StringBuilder stringBuilder = new StringBuilder();

        for(FieldError error : errors) {
            stringBuilder.append(error.getField());
            stringBuilder.append(": ");
            stringBuilder.append(error.getDefaultMessage());
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
