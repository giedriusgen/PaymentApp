package com.payment.dto.payment;

import com.payment.enums.currency.PaymentCurrency;
import com.payment.enums.paymentType.PaymentType;
import com.payment.validation.PaymentRequestValidation;

import javax.validation.constraints.NotNull;

@PaymentRequestValidation
public class PaymentRequestDto {

    private Double amount;
    @NotNull(message = "Debtor_iban value should be specified")
    private String debtor_iban;
    @NotNull(message = "Creditor_iban value should be specified")
    private String creditor_iban;
    @NotNull(message = "PaymentType value should be specified")
    private PaymentType paymentType;
    @NotNull(message = "Currency value should be specified")
    private PaymentCurrency paymentCurrency;
    private String details;
    private String creditor_bank_BIC_code;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentCurrency getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(PaymentCurrency paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getDebtor_iban() {
        return debtor_iban;
    }

    public void setDebtor_iban(String debtor_iban) {
        this.debtor_iban = debtor_iban;
    }

    public String getCreditor_iban() {
        return creditor_iban;
    }

    public void setCreditor_iban(String creditor_iban) {
        this.creditor_iban = creditor_iban;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreditor_bank_BIC_code() {
        return creditor_bank_BIC_code;
    }

    public void setCreditor_bank_BIC_code(String creditor_bank_BIC_code) {
        this.creditor_bank_BIC_code = creditor_bank_BIC_code;
    }
}
