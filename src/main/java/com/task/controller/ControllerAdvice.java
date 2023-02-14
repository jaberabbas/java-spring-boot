package com.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.model.ErrorCodes;
import com.task.model.ErrorMessage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.validation.FieldError;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        StringBuilder sbErros = new StringBuilder();
        errors.forEach((fieldName, errorMessage) -> sbErros.append(fieldName + " : " + errorMessage));
        ErrorMessage message = new ErrorMessage(ErrorCodes.FUNC001.getCode(), ErrorCodes.FUNC001.getDesc(), "", sbErros.toString());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ErrorMessage> handleWebClientRequestException(WebClientRequestException e) {
        String details = "request method " + e.getMethod() + " failed: " + e.getMessage();
        ErrorMessage errorMessage = new ErrorMessage(ErrorCodes.TEC001.getCode(), ErrorCodes.TEC001.getDesc(), "", details);
        return ResponseEntity.internalServerError().body(errorMessage);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorMessage> handleWebClientResponseException(WebClientResponseException e) {
        ErrorMessage errorMessage;
        int httpStatusCode = e.getStatusCode().value();
        ObjectMapper mapper = new ObjectMapper();
        try {
            errorMessage = mapper.readValue(e.getResponseBodyAsString(), ErrorMessage.class);
            if (httpStatusCode == 400) return ResponseEntity.badRequest().body(errorMessage);
            else return ResponseEntity.internalServerError().body(errorMessage);

        } catch (JsonProcessingException ex) {
            if (httpStatusCode == 422) {
                return ResponseEntity.unprocessableEntity().build();
            } else if (httpStatusCode == 204) {
                return ResponseEntity.noContent().build();
            }
            String details = "JsonProcessingException while processing WebClientResponseException " + e.getMessage() + " status code: " + httpStatusCode;
            errorMessage = new ErrorMessage(ErrorCodes.FUNC001.getCode(), ErrorCodes.FUNC001.getDesc(), "", details);
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }

}
