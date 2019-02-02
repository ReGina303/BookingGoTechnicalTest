package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Parameters {
    @Id
    @GeneratedValue
    private Long id;

    double picklat;
    double picklong;
    double droplat;
    double droplong;
    int passengers;

    /**
     * Default constructor
     */
    public Parameters() {}

    /**
     * Getter
     * @return Drop off latitude
     */
    public double getDroplat() {
        return droplat;
    }

    /**
     * Getter
     * @return Drop off longitude
     */
    public double getDroplong() {
        return droplong;
    }

    /**
     * Getter
     * @return Pick up latitude
     */
    public double getPicklat() {
        return picklat;
    }

    /**
     * Getter
     * @return Pick up longitude
     */
    public double getPicklong() {
        return picklong;
    }

    /**
     * Getter
     * @return Number of passengers
     */
    public int getPassengers() {
        return passengers;
    }

    /**
     * Setter
     * @param id Required ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Setter
     * @param droplat Required drop off latitude
     */
    public void setDroplat(double droplat) {
        this.droplat = droplat;
    }

    /**
     * Setter
     * @param droplong Required drop off longitude
     */
    public void setDroplong(double droplong) {
        this.droplong = droplong;
    }

    /**
     * Setter
     * @param passengers Required number of passengers
     */
    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    /**
     * Setter
     * @param picklat Required pick up latitude
     */
    public void setPicklat(double picklat) {
        this.picklat = picklat;
    }

    /**
     * Setter
     * @param picklong Required pick up longitude
     */
    public void setPicklong(double picklong) {
        this.picklong = picklong;
    }
}
