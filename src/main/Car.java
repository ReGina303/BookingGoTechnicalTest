/**
 * Class representing a car
 * Last updated: 02.02.2019
 *
 * @author Nikolett Bakos
 */
package main;

public class Car {
    private final String carType;
    private final int noOfPassengers;

    /**
     * Constructor
     * @param requiredCarType The required car type
     */
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

    /**
     * Getter number of passengers
     * @return The maximum number of passengers that the car can hold
     */
    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    /**
     * Getter car type
     * @return Name of the car type
     */
    public String getCarType() {
        return carType;
    }
}
