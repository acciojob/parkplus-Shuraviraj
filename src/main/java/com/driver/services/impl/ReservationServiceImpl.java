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

import java.util.Comparator;
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
        SpotType spotType;
        switch (numberOfWheels) {
            case 1:
            case 2: {
                spotType = SpotType.TWO_WHEELER;
                break;
            }
            case 3:
            case 4: {
                spotType = SpotType.FOUR_WHEELER;
                break;
            }
            default: {
                spotType = SpotType.OTHERS;
                break;
            }
        }

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository3.findById(parkingLotId);
        if (!optionalParkingLot.isPresent()) throw new Exception("Cannot make reservation");

        Optional<User> optionalUser = userRepository3.findById(userId);
        if (!optionalUser.isPresent()) throw new Exception("Cannot make reservation");

        Spot spot = optionalParkingLot.get().getSpotList().stream()
                .filter(s -> {
                    switch (spotType) {
                        case TWO_WHEELER: {
                            return true;
                        }
                        case FOUR_WHEELER: {
                            if (s.getSpotType() == SpotType.FOUR_WHEELER || s.getSpotType() == SpotType.OTHERS)
                                return true;
                            break;
                        }
                        case OTHERS: {
                            if (s.getSpotType() == SpotType.OTHERS) return true;
                            break;
                        }
                    }
                    return false;
                }).min(Comparator.comparingInt(Spot::getPricePerHour)).orElseThrow(() -> new Exception("Cannot make reservation"));

        Reservation reservationTobeSaved = ReservationTransformer.toReservation(optionalUser.get(), timeInHours, spot);
        reservationRepository3.save(reservationTobeSaved);
        return reservationTobeSaved;

    }
}
