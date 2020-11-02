package com.payment.dto.payment;

import com.payment.enums.currency.PaymentCurrency;
import com.payment.enums.paymentType.PaymentType;
import com.payment.validation.PaymentRequestValidation;

import javax.validation.constraints.NotNull;

@PaymentRequestValidation
public class PaymentRequestDto {

    private Double amount;
    @NotNull(message = "DebtorIban value should be specified")
    private String debtorIban;
    @NotNull(message = "CreditorIban value should be specified")
    private String creditorIban;
    @NotNull(message = "PaymentType value should be specified")
    private PaymentType paymentType;
    @NotNull(message = "Currency value should be specified")
    private PaymentCurrency paymentCurrency;
    private String details;
    private String creditorBankBICCode;

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

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
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

    public String getCreditorBankBICCode() {
        return creditorBankBICCode;
    }

    public void setCreditorBankBICCode(String creditorBankBICCode) {
        this.creditorBankBICCode = creditorBankBICCode;
    }
}
