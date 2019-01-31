/**
 * A class represents a pair of car and price to help improve the sorting
 * Last updated: 30.01.2019
 *
 * @author Nikolett Bakos
 */
package main;

public class Offer {
    private final String car;
    private final int price;

    /**
     * Constructor
     * @param requiredCar The required car
     * @param requiredPrice The required price
     */
    public Offer (String requiredCar, int requiredPrice) {
        car = requiredCar;
        price = requiredPrice;
    }

    /**
     * Get the type of car
     * @return Type of the car
     */
    public String getCar () {
        return car;
    }

    /**
     * Get the price of the car
     * @return Price of the car
     */
    public int getPrice () {
        return price;
    }

    /**
     * Comparing this car against a given other one basing the comparision on prices, then the car types
     * @param other The car with what it needs to be compared
     * @return Usual int result (-, 0, +)
     */
    public int compareTo (Offer other) {
        if (price == other.price)
            return car.compareTo(other.car);
        else
            return price - other.price;
    }

    @Override
    public String toString() {
        return "{" + car + "} - {" + price + "}";
    }
}
