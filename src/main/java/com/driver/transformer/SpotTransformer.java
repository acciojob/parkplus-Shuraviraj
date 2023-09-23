package com.driver.transformer;

import com.driver.model.Spot;
import com.driver.model.SpotType;

import java.util.ArrayList;

public class SpotTransformer {

    public static Spot toSpot(Integer numberOfWheels, Integer pricePerHour) {
        SpotType spotType;
        switch (numberOfWheels) {
            case 1:
            case 2:
                spotType = SpotType.TWO_WHEELER;
                break;
            case 3:
            case 4:
                spotType = SpotType.FOUR_WHEELER;
                break;
            default:
                spotType = SpotType.OTHERS;
                break;
        }
        return new Spot(spotType, pricePerHour, false, null, new ArrayList<>());
    }
}
