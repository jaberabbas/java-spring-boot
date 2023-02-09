package com.javalab.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import com.javalab.model.ErrorCodes;
import com.javalab.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        StringBuilder sbKeyValueErrors = new StringBuilder();
        errors.forEach((fieldName, errorMessage)->sbKeyValueErrors.append(fieldName + " " + errorMessage));
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.FUNC001.getCode(), ErrorCodes.FUNC001.getDesc(), "", sbKeyValueErrors.toString());
        log.error("MethodArgumentNotValidException was thrown: " + sbKeyValueErrors);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.FUNC001.getCode(), ErrorCodes.FUNC001.getDesc(), String.valueOf(e.getErrorCode()), e.getMessage());
        log.error("SQLIntegrityConstraintViolationException: "  + e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}

