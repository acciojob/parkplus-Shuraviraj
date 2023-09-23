package com.driver.transformer;

import com.driver.entities.Reservation;
import com.driver.entities.Spot;
import com.driver.entities.User;

public class ReservationTransformer {

    public static Reservation toReservation(User user, Integer timeInHours, Spot spot) {
        return new Reservation(timeInHours, user, spot, null);
    }
}
