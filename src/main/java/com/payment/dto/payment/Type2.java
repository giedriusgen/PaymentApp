package com.payment.dto.payment;

import javax.persistence.Entity;

@Entity
public class Type2 extends Payment {

    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
