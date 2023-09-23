package com.driver.transformer;

import com.driver.model.ParkingLot;

import java.util.ArrayList;

public class ParkingLotTransformer {
    public static ParkingLot toParkingLot(String name, String address) {
        return new ParkingLot(name, address, new ArrayList<>());
    }
}
