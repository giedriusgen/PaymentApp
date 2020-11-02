package com.payment.validation;

import com.payment.dto.payment.PaymentRequestDto;
import com.payment.enums.currency.PaymentCurrency;
import com.payment.enums.paymentType.PaymentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import fr.marcwrobel.jbanking.bic.Bic;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

import static com.payment.errors.messages.ErrorMessages.*;

public class PaymentRequestValidator implements
        ConstraintValidator<PaymentRequestValidation, PaymentRequestDto> {

    private boolean isValid;

    @Override
    public void initialize(PaymentRequestValidation paymentRequest) {
    }

    @Override
    public boolean isValid(PaymentRequestDto dto, ConstraintValidatorContext context) {
        isValid = true;
        context.disableDefaultConstraintViolation();
        validateAmount(dto, context);
        validateType1(dto, context);
        validateType2(dto, context);
        validateDetails(dto, context);
        validateBICParameter(dto, context);
        validateIBAN(dto, context);
        return isValid;
    }

    private void validateAmount(PaymentRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getAmount() == null) {
            context.buildConstraintViolationWithTemplate(AMOUNT_SHOULD_BE_SPECIFIED)
                    .addPropertyNode("amount")
                    .addConstraintViolation();
            isValid = false;
        } else if (dto.getAmount() <= 0) {
            context.buildConstraintViolationWithTemplate(AMOUNT_SHOULD_BE_HIGHER_THAN_0)
                    .addPropertyNode("amount")
                    .addConstraintViolation();
            isValid = false;
        } else if (BigDecimal.valueOf(dto.getAmount()).scale() > 2) {
            context.buildConstraintViolationWithTemplate(AMOUNT_SHOULD_NOT_HAVE_MORE_THAN_2_DECIMALS)
                    .addPropertyNode("amount")
                    .addConstraintViolation();
            isValid = false;
        }
    }

    private void validateType1(PaymentRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getPaymentType().equals(PaymentType.TYPE1)) {
            if (!dto.getPaymentCurrency().equals(PaymentCurrency.EUR)) {
                context.buildConstraintViolationWithTemplate(CURRENCY_VALUE_SHOULD_BE_EUR_FOR_TYPE1)
                        .addPropertyNode("paymentCurrency")
                        .addConstraintViolation();
                isValid = false;
            }
        }
    }

    private void validateType2(PaymentRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getPaymentType().equals(PaymentType.TYPE2)) {
            if (!dto.getPaymentCurrency().equals(PaymentCurrency.USD)) {
                context.buildConstraintViolationWithTemplate(CURRENCY_VALUE_SHOULD_BE_USD_FOR_TYPE2)
                        .addPropertyNode("paymentCurrency")
                        .addConstraintViolation();
                isValid = false;
            }
        }
    }

    private void validateDetails(PaymentRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getPaymentType().equals(PaymentType.TYPE1)) {
            if (dto.getDetails() == null) {
                context.buildConstraintViolationWithTemplate(DETAILS_VALUE_SHOULD_BE_SPECIFIED_FOR_TYPE1)
                        .addPropertyNode("details")
                        .addConstraintViolation();
                isValid = false;
            }
        } else if (dto.getPaymentType().equals(PaymentType.TYPE3)) {
            if (dto.getDetails() != null) {
                context.buildConstraintViolationWithTemplate(DETAILS_VALUE_SHOULD_NOT_BE_SPECIFIED_FOR_TYPE3)
                        .addPropertyNode("details")
                        .addConstraintViolation();
                isValid = false;
            }
        }
    }

    private void validateBICParameter(PaymentRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getPaymentType().equals(PaymentType.TYPE3)) {
            if (dto.getCreditorBankBICCode() == null) {
                context.buildConstraintViolationWithTemplate(CREDITOR_BANK_BIC_CODE_SHOULD_BE_SPECIFIED_FOR_TYPE3)
                        .addPropertyNode("creditorBankBICCode")
                        .addConstraintViolation();
                isValid = false;
            } else {
                validateBICCode(dto, context);
            }
        } else {
            if (dto.getCreditorBankBICCode() != null) {
                context.buildConstraintViolationWithTemplate(CREDITOR_BANK_BIC_CODE_SHOULD_BE_SPECIFIED_ONLY_FOR_TYPE3)
                        .addPropertyNode("creditorBankBICCode")
                        .addConstraintViolation();
                isValid = false;
            }
        }
    }

    private void validateBICCode(PaymentRequestDto dto, ConstraintValidatorContext context) {
        if (!Bic.isValid(dto.getCreditorBankBICCode())) {
            context.buildConstraintViolationWithTemplate(CREDITOR_BANK_BIC_CODE_IS_NOT_VALID)
                    .addPropertyNode("creditorBankBICCode")
                    .addConstraintViolation();
            isValid = false;
        }
    }

    private void validateIBAN(PaymentRequestDto dto, ConstraintValidatorContext context) {
        boolean creditorIbanValid = true;
        boolean debtorIbanValid = true;
        String creditorIban = dto.getCreditorIban();
        String debtorIban = dto.getDebtorIban();

        IBANCheckDigit ibanCheckDigit = new IBANCheckDigit();

        if (!ibanCheckDigit.isValid(creditorIban)) {
            context.buildConstraintViolationWithTemplate(CREDITOR_IBAN_IS_NOT_VALID)
                    .addPropertyNode("creditorIban")
                    .addConstraintViolation();
            isValid = false;
            creditorIbanValid = false;
        }
        if (!ibanCheckDigit.isValid(debtorIban)) {
            context.buildConstraintViolationWithTemplate(DEBTOR_IBAN_IS_NOT_VALID)
                    .addPropertyNode("debtorIban")
                    .addConstraintViolation();
            isValid = false;
            debtorIbanValid = false;
        }
        if (creditorIbanValid && debtorIbanValid && creditorIban.equals(debtorIban)) {
            context.buildConstraintViolationWithTemplate(DEBTOR_IBAN_AND_CREDITOR_IBAN_SHOULD_BE_DIFFERENT)
                    .addConstraintViolation();
            isValid = false;
        }
    }
}
