/**
 * Class representing a car
 * Last updated: 31.01.2019
 *
 * @author Nikolett Bakos
 */
package main;

public class Car {
    private final String carType;
    private final int noOfPassengers;

    public Car (String requiredCarType) {
        carType = requiredCarType;
        switch(carType) {
            case "STANDARD":
                noOfPassengers = 4;
                break;
            case "EXECUTIVE":
                noOfPassengers = 4;
                break;
            case "LUXURY":
                noOfPassengers = 4;
                break;
            case "PEOPLE_CARRIER":
                noOfPassengers = 6;
                break;
            case "LUXURY_PEOPLE_CARRIER":
                noOfPassengers = 6;
                break;
            case "MINIBUS":
                noOfPassengers = 16;
                break;
            default:
                noOfPassengers = 4;
        }
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public String getCarType() {
        return carType;
    }
}
