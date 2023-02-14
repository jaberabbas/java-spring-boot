package com.entity.mapper;

import com.entity.model.CustomException;
import com.entity.model.ErrorMessage;

public class ErrorMessageMapper {
    

    public static ErrorMessage toErrorMessage(CustomException customException) {
        if(customException == null) return null;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(customException.getStatusCode());
        errorMessage.setMessage(customException.getErrorMessage());
        errorMessage.setSubCode(customException.getSubCode());
        errorMessage.setDetails(customException.getDetails());
        return errorMessage;
    }
}
