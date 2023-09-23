package com.driver.transformer;

import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.User;

public class ReservationTransformer {

    public static Reservation toReservation(User user, Integer timeInHours, Spot spot) {
        return new Reservation(timeInHours, user, spot, null);
    }
}
