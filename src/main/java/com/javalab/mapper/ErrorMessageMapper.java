package com.javalab.mapper;

import com.javalab.model.CustomException;
import com.javalab.model.ErrorMessage;

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
