package com.example.demo.controller;

import com.example.demo.model.ErrorCodes;
import com.example.demo.model.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.PersistentObjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


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
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.FUNC001.getCode(), ErrorCodes.FUNC001.getDesc(), String.valueOf(e.getErrorCode()), e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handlePersistentObjectException(PersistentObjectException e) {
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.FUNC001.getCode(), ErrorCodes.FUNC001.getDesc(), String.valueOf(e.hashCode()), e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.FUNC001.getCode(), ErrorCodes.FUNC001.getDesc(), String.valueOf(e.hashCode()), e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ExceptionHandler(java.net.URISyntaxException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(java.net.URISyntaxException e) {
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.TEC001.getCode(), ErrorCodes.TEC001.getDesc(), String.valueOf(e.hashCode()), e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}

