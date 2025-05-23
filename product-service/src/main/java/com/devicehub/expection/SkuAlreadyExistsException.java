package com.devicehub.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SkuAlreadyExistsException extends RuntimeException{

    public SkuAlreadyExistsException(String message) {
        super(message);
    }
}
