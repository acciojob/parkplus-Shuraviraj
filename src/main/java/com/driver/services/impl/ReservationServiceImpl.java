package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.model.User;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import com.driver.transformer.ReservationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;

    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        SpotType mySpotType;
        switch (numberOfWheels) {
            case 1:
            case 2: {
                mySpotType = SpotType.TWO_WHEELER;
                break;
            }
            case 3:
            case 4: {
                mySpotType = SpotType.FOUR_WHEELER;
                break;
            }
            default: {
                mySpotType = SpotType.OTHERS;
                break;
            }
        }

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository3.findById(parkingLotId);
        if (!optionalParkingLot.isPresent()) throw new Exception("Cannot make reservation");

        Optional<User> optionalUser = userRepository3.findById(userId);
        if (!optionalUser.isPresent()) throw new Exception("Cannot make reservation");

        List<Spot> spotList = new ArrayList<>(optionalParkingLot.get().getSpotList());
        spotList.sort(Comparator.comparingInt(Spot::getPricePerHour));

        Spot bestSpot = null;
        for (Spot s : spotList) {
            if (mySpotType == SpotType.OTHERS) {
                if (s.getSpotType() != SpotType.OTHERS) continue;
                bestSpot = s;
                break;
            } else if (mySpotType == SpotType.FOUR_WHEELER) {
                if (s.getSpotType() == SpotType.FOUR_WHEELER || s.getSpotType() == SpotType.OTHERS) {
                    bestSpot = s;
                    break;
                }
            } else {
                bestSpot = s;
                break;
            }
        }
        if (bestSpot == null) throw new Exception("Cannot make reservation");

        Reservation reservationTobeSaved = ReservationTransformer.toReservation(optionalUser.get(), timeInHours, bestSpot);
        reservationRepository3.save(reservationTobeSaved);
        return reservationTobeSaved;

    }
}
