package com.driver.transformer;

import com.driver.entities.Payment;
import com.driver.entities.Reservation;
import com.driver.model.PaymentMode;

public class PaymentTransformer {
    public static Payment toPayment(Reservation reservation, String mode) {
        PaymentMode paymentMode = null;
        switch (mode) {
            case "cash":
                paymentMode = PaymentMode.CASH;
                break;
            case "card":
                paymentMode = PaymentMode.CARD;
                break;
            case "upi":
                paymentMode = PaymentMode.UPI;
                break;
            default:
        }
        return new Payment(true, paymentMode, reservation);
    }
}
