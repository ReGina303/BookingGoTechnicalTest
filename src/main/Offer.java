/**
 * A class represents a pair of car and price to help improve the sorting
 * Last updated: 02.02.2019
 *
 * @author Nikolett Bakos
 */
package main;

public class Offer {
    private final Car car;
    private final String supplier;
    private final int price;

    /**
     * Constructor
     * @param requiredCar The required car
     * @param requiredPrice The required price
     */
    public Offer (String requiredCar, String requiredSupplier, int requiredPrice) {
        car = new Car(requiredCar);
        supplier = requiredSupplier;
        price = requiredPrice;
    }

    /**
     * Get the type of car
     * @return Type of the car
     */
    public String getCar () {
        return car.getCarType();
    }

    /**
     * Get the price of the car
     * @return Price of the car
     */
    public int getPrice () {
        return price;
    }

    /**
     * Get the maximum number of passengers that the car can hold
     * @return The maximum number of passengers that the car can hold
     */
    public int getNoOfPassengers () {
        return car.getNoOfPassengers();
    }

    /**
     * Comparing this car against a given other one basing the comparision on prices, then the car types
     * @param other The car with what it needs to be compared
     * @return Usual int result (-, 0, +)
     */
    public int compareTo (Offer other) {
        if (price == other.price)
            return car.getCarType().compareTo(other.car.getCarType());
        else
            return price - other.price;
    }

    @Override
    public String toString() {
        return "{" + car.getCarType() + "} - {" + supplier + "} - {" + price + "}";
    }
}
