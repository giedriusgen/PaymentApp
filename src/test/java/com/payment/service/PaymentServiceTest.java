package com.payment.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    private static PaymentService paymentService;
    private static Date date1;
    private static Date date2;
    private static Date date3;
    private static Date date4;
    private static Date date5;

    @BeforeAll
    public static void setUp() {
        paymentService = new PaymentService();
        date1 = new GregorianCalendar(2020, Calendar.NOVEMBER, 01, 00, 00).getTime();
        date2 = new GregorianCalendar(2020, Calendar.NOVEMBER, 01, 00, 59).getTime();
        date3 = new GregorianCalendar(2020, Calendar.NOVEMBER, 01, 23, 59).getTime();
        date4 = new GregorianCalendar(2020, Calendar.NOVEMBER, 02, 18, 58).getTime();
        date5 = new GregorianCalendar(2020, Calendar.NOVEMBER, 02, 23, 59).getTime();
    }

    @Test
    public void shouldBeTheSameDate() {
        assertTrue(paymentService.isSameDay(date1, date2), "(" + date1 + ") and (" + date2 + ") must belong to the same date");
        assertTrue(paymentService.isSameDay(date1, date3), "(" + date1 + ") and (" + date3 + ") must belong to the same date");
    }

    @Test
    public void shouldNotBeTheSameDate() {
        assertFalse(paymentService.isSameDay(date1, date4), "(" + date1 + ") and (" + date4 + ") must belong to the different dates");
        assertFalse(paymentService.isSameDay(date3, date4), "(" + date3 + ") and (" + date4 + ") must belong to the different dates");
    }

    @Test
    public void shouldCalculate0HoursBetweenTwoDates() {
        assertEquals(0, paymentService.calculateHoursBetweenTwoDates(date1, date2), "It must be 0 full hours between (" + date1 + ") and (" + date2 + ")");
        assertEquals(0, paymentService.calculateHoursBetweenTwoDates(date4, date2), "Since (" + date4 + ") is before (" + date2 + ") it must be 0 full hours between these dates");
    }

    @Test
    public void shouldCalculateCorrectHoursBetweenTwoDates() {
        assertEquals(23, paymentService.calculateHoursBetweenTwoDates(date1, date3), "It must be 23 full hours between (" + date1 + ") and (" + date3 + ")");
        assertEquals(5, paymentService.calculateHoursBetweenTwoDates(date4, date5), "It must be 5 full hours between (" + date4 + ") and (" + date5 + ")");
    }

}
