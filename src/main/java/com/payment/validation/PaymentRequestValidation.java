package com.payment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Documented
@Constraint(validatedBy = PaymentRequestValidator.class)
@Target( { ElementType.TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentRequestValidation {
    String message() default "Invalid payment request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}