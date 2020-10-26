package com.payment.dto.payment;

public class PaymentAmountResponseDto {

    private Long id;
    private double amount;

    public PaymentAmountResponseDto(Long id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public PaymentAmountResponseDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
