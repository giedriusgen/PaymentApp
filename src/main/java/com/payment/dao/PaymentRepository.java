package com.payment.dao;

import com.payment.dto.payment.Payment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByCanceledFalse(Sort sort);

}
