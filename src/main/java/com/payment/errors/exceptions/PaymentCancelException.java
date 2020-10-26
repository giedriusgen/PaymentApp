package com.payment.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class PaymentCancelException extends RuntimeException {

    public PaymentCancelException(String exception) {
        super(exception);
    }

}