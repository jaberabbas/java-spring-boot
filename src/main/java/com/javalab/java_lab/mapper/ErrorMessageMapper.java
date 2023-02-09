package com.javalab.java_lab.mapper;

import com.javalab.java_lab.model.CustomException;
import com.javalab.java_lab.model.ErrorMessage;

public class ErrorMessageMapper {
    

    public static ErrorMessage toErrorMessage(CustomException customException) {
        if(customException == null) return null;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(customException.getStatusCode());
        errorMessage.setErrorMessage(customException.getErrorMessage());
        errorMessage.setSubCode(customException.getSubCode());
        errorMessage.setDetails(customException.getDetails());
        return errorMessage;
    }
}
