/**
 * A class represents an offer
 * Last updated: 02.02.2019
 *
 * @author Nikolett Bakos
 */
package com.example.demo.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OfferEntity {
    private @Id @GeneratedValue Long id;

    private String car;
    private String supplier;
    private int price;

    /**
     * Constructor
     * @param requiredCar The required car
     * @param requiredPrice The required price
     */
    public OfferEntity(String requiredCar, String requiredSupplier, int requiredPrice) {
        car = requiredCar;
        supplier = requiredSupplier;
        price = requiredPrice;
    }

    /**
     * Default constructor
     */
    public OfferEntity() {}


    /**
     * Getter for the ID
     * @return The ID of the offer
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the type of car
     * @return Type of the car
     */
    public String getCar () {
        return car;
    }

    /**
     * Getter to supplier
     * @return Supplier of the offer
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Get the price of the car
     * @return Price of the car
     */
    public int getPrice () {
        return price;
    }

    /**
     * Setter for car type
     * @param car Required car type
     */
    public void setCar(String car) {
        this.car = car;
    }

    /**
     * Setter for the price
     * @param price Required price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Setter for the supplier
     * @param supplier Required supplier
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * Setter for the ID
     * @param id Required ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Comparing this car against a given other one basing the comparision on prices, then the car types
     * other The car with what it needs to be compared. Used for bubblesort
     * @return Usual int result (-, 0, +)
     */
    public int compareTo (OfferEntity other) {
        if (price == other.price)
            return car.compareTo(other.car);
        else
            return price - other.price;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
