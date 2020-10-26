package com.payment.dto.payment;

public class PaymentCancellationResponseDto {

    private Long id;
    private Double cancellationFee;

    public PaymentCancellationResponseDto(){}

    public PaymentCancellationResponseDto(Long id, Double cancellationFee) {
        this.id = id;
        this.cancellationFee = cancellationFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

}
