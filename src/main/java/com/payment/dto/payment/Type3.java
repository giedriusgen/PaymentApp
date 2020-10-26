package com.payment.dto.payment;

import javax.persistence.Entity;

@Entity
public class Type3 extends Payment {

    private String creditor_bank_BIC_code;

    public String getCreditor_bank_BIC_code() {
        return creditor_bank_BIC_code;
    }

    public void setCreditor_bank_BIC_code(String creditor_bank_BIC_code) {
        this.creditor_bank_BIC_code = creditor_bank_BIC_code;
    }

}
