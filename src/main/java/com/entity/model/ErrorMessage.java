package com.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class ErrorMessage {

    private String code;
    private String message;
    private String subCode;
    private String details;

}