package com.payment.service;

import com.payment.dao.PaymentRepository;
import com.payment.dto.payment.*;
import com.payment.enums.currency.PaymentCurrency;
import com.payment.enums.paymentType.PaymentType;
import com.payment.errors.exceptions.PaymentCancelException;
import com.payment.errors.exceptions.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.payment.errors.messages.ErrorMessages.*;


@Service
public class PaymentService {

    public static final double TYPE1_COEFFICIENT = 0.05;
    public static final double TYPE2_COEFFICIENT = 0.1;
    public static final double TYPE3_COEFFICIENT = 0.15;
    public static final double EUR_FROM_USD_VALUE = 0.85;
    public static final double EUR_VALUE = 1;

    private PaymentRepository paymentRepository;

    @Transactional
    public void createPayment(PaymentRequestDto paymentDto) {
        switch (paymentDto.getPaymentType()) {
            case TYPE1:
                Type1 type1 = new Type1();
                type1.setDetails(paymentDto.getDetails());
                type1.setAmount(paymentDto.getAmount());
                type1.setCreditor_iban(paymentDto.getCreditor_iban());
                type1.setDebtor_iban(paymentDto.getDebtor_iban());
                type1.setPaymentCurrency(paymentDto.getPaymentCurrency());
                type1.setPaymentType(paymentDto.getPaymentType());
                type1.setCreationTimeStamp(new Date());
                paymentRepository.save(type1);
                break;
            case TYPE2:
                Type2 type2 = new Type2();
                type2.setDetails(paymentDto.getDetails());
                type2.setAmount(paymentDto.getAmount());
                type2.setCreditor_iban(paymentDto.getCreditor_iban());
                type2.setDebtor_iban(paymentDto.getDebtor_iban());
                type2.setPaymentCurrency(paymentDto.getPaymentCurrency());
                type2.setPaymentType(paymentDto.getPaymentType());
                type2.setCreationTimeStamp(new Date());
                paymentRepository.save(type2);
                break;
            case TYPE3:
                Type3 type3 = new Type3();
                type3.setCreditor_bank_BIC_code(paymentDto.getCreditor_bank_BIC_code());
                type3.setAmount(paymentDto.getAmount());
                type3.setCreditor_iban(paymentDto.getCreditor_iban());
                type3.setDebtor_iban(paymentDto.getDebtor_iban());
                type3.setPaymentCurrency(paymentDto.getPaymentCurrency());
                type3.setPaymentType(paymentDto.getPaymentType());
                type3.setCreationTimeStamp(new Date());
                paymentRepository.save(type3);
                break;
        }
    }

    @Transactional(readOnly = true)
    public PaymentCancellationResponseDto getPaymentWithCancellationFee(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        PaymentCancellationResponseDto response = new PaymentCancellationResponseDto(payment.get().getId(), payment.get().getCancellationFee());
        return response;
    }

    @Transactional(readOnly = true)
    public List<PaymentAmountResponseDto> getNotCanceledPaymentsOrderedByAmountDesc() {
        return paymentRepository.findByCanceledFalseOrderByAmountDesc().stream()
                .map((payment -> new PaymentAmountResponseDto(payment.getId(), payment.getAmount())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PaymentAmountResponseDto> getNotCanceledPaymentsOrderedByAmountAsc() {
        return paymentRepository.findByCanceledFalseOrderByAmountAsc().stream()
                .map((payment -> new PaymentAmountResponseDto(payment.getId(), payment.getAmount())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelPayment(Long id) {
        int hoursBetweenDates;
        BigDecimal cancellationFee;
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            if (Boolean.TRUE != payment.isCanceled()) {
                Date cancellationDate = new Date();
                if (isSameDay(payment.getCreationTimeStamp(), cancellationDate)) {
                    hoursBetweenDates = calculateHoursBetweenTwoDates(payment.getCreationTimeStamp(), cancellationDate);
                    if (hoursBetweenDates > 0) {
                        cancellationFee = calculateCancellationFee(payment.getPaymentType(), payment.getPaymentCurrency(), hoursBetweenDates);
                    } else {
                        cancellationFee = BigDecimal.ZERO;
                    }
                    payment.setCanceled(true);
                    payment.setCancellationFee(cancellationFee.doubleValue());
                    payment.setCancellationDate(cancellationDate);
                    paymentRepository.save(payment);
                } else {
                    throw new PaymentCancelException(NOT_POSSIBLE_TO_CANCEL_PAYMENT);
                }
            } else {
                throw new PaymentCancelException(PAYMENT_IS_ALREADY_CANCELLED);
            }
        } else {
            throw new PaymentNotFoundException(PAYMENT_ID_NOT_FOUND);
        }
    }

    public boolean isSameDay(Date startTime, Date endTime) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(startTime);
        cal2.setTime(endTime);
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        return sameDay;
    }

    public int calculateHoursBetweenTwoDates(Date startTime, Date endTime) {
        int hours = 0;
        if (startTime.before(endTime)) {
            long secs = (endTime.getTime() - startTime.getTime()) / 1000;
            hours = (int) (secs / 3600);
        }
        return hours;
    }

    public BigDecimal calculateCancellationFee(PaymentType type, PaymentCurrency currency, int hours) {
        BigDecimal cancellationFee;
        switch (type) {
            case TYPE1:
                cancellationFee = BigDecimal.valueOf(TYPE1_COEFFICIENT).multiply(BigDecimal.valueOf(hours).multiply(BigDecimal.valueOf(EUR_VALUE)));
                break;
            case TYPE2:
                cancellationFee = BigDecimal.valueOf(TYPE2_COEFFICIENT).multiply(BigDecimal.valueOf(hours).multiply(BigDecimal.valueOf(EUR_FROM_USD_VALUE)));
                break;
            case TYPE3:
                cancellationFee = BigDecimal.valueOf(TYPE3_COEFFICIENT).multiply(BigDecimal.valueOf(hours)
                        .multiply(BigDecimal.valueOf(resolveCurrencyCoefficient(currency))));
                break;
            default:
                cancellationFee = BigDecimal.ZERO;
                break;
        }
        return cancellationFee;
    }

    public double resolveCurrencyCoefficient(PaymentCurrency currency) {
        double currencyCoefficient;
        switch (currency) {
            case EUR:
                currencyCoefficient = EUR_VALUE;
                break;
            case USD:
                currencyCoefficient = EUR_FROM_USD_VALUE;
                break;
            default:
                currencyCoefficient = 0;
        }
        return currencyCoefficient;
    }


    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


}
