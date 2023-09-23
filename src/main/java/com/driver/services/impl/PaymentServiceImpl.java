package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import com.driver.transformer.PaymentTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        mode = mode.toLowerCase();
        if (!(mode.equals("cash") || "card".equals(mode) || "upi".equals(mode))) {
            throw new Exception("Payment mode not detected");
        }
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        Spot spot = reservation.getSpot();
        int totalAmount = reservation.getNumberOfHours() * spot.getPricePerHour();
        if (totalAmount > amountSent) {
            throw new Exception("Insufficient Amount");
        }
        Payment payment = PaymentTransformer.toPayment(reservation, mode);
        reservation.setPayment(payment);
        payment.setReservation(reservation);
        reservationRepository2.save(reservation);
        return payment;
    }
}
