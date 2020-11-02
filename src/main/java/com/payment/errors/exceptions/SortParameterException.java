package com.payment.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class SortParameterException extends RuntimeException {

    public SortParameterException(String exception) {
        super(exception);
    }
}