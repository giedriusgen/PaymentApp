package com.payment.errors.messages;

public class ErrorMessages {
    public static final String PAYMENT_ID_NOT_FOUND = "Payment id not found";
    public static final String PAYMENT_IS_ALREADY_CANCELLED = "Payment is already cancelled";
    public static final String NOT_POSSIBLE_TO_CANCEL_PAYMENT = "It is not possible to cancel payment because payment do not happened today";
    public static final String AMOUNT_SHOULD_BE_SPECIFIED = "Amount should be specified";
    public static final String AMOUNT_SHOULD_BE_HIGHER_THAN_0 = "Amount should be higher than 0";
    public static final String AMOUNT_SHOULD_NOT_HAVE_MORE_THAN_2_DECIMALS = "Amount should not have more that 2 decimals";
    public static final String CURRENCY_VALUE_SHOULD_BE_EUR_FOR_TYPE1 = "Currency value should be EUR for PaymentType: TYPE1";
    public static final String DETAILS_VALUE_SHOULD_BE_SPECIFIED_FOR_TYPE1 = "Details value should be specified for PaymentType: TYPE1";
    public static final String DETAILS_VALUE_SHOULD_NOT_BE_SPECIFIED_FOR_TYPE3 = "Details value should not be specified for PaymentType: TYPE3";
    public static final String CURRENCY_VALUE_SHOULD_BE_USD_FOR_TYPE2 = "Currency value should be USD for PaymentType: TYPE2";
    public static final String CREDITOR_BANK_BIC_CODE_SHOULD_BE_SPECIFIED_FOR_TYPE3 = "CreditorBankBICCode value should be specified for PaymentType: TYPE3";
    public static final String CREDITOR_BANK_BIC_CODE_SHOULD_BE_SPECIFIED_ONLY_FOR_TYPE3 = "CreditorBankBICCode value should be specified only for PaymentType: TYPE3";
    public static final String CREDITOR_IBAN_IS_NOT_VALID = "Provided creditorIban is not valid";
    public static final String CREDITOR_BANK_BIC_CODE_IS_NOT_VALID = "Provided creditorBankBICCode is not valid";
    public static final String DEBTOR_IBAN_IS_NOT_VALID = "Provided debtorIban is not valid";
    public static final String DEBTOR_IBAN_AND_CREDITOR_IBAN_SHOULD_BE_DIFFERENT = "Provided debtorIban and creditorIban should be different";
    public static final String INCORRECT_SORT_PARAMETER = "Provided sort parameter is incorrect. Possible values: creationTimeStamp, amount";

}
