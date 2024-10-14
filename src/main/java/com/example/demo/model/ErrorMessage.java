package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ErrorMessage {

    private String code;
    private String message;
    private String subCode;
    private String details;
    public ErrorMessage(String code, String message, String subCode, String details){
        this.code = code;
        this.message = message;
        this.subCode = subCode;
        this.details = details;
    }

}
