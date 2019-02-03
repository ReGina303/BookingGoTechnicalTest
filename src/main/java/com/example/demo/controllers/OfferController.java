/**
 * Class represents the rest controller of offers
 */
package com.example.demo.controllers;

import com.example.demo.calculator.OfferCalculator;
import com.example.demo.dao.OfferRepository;
import com.example.demo.entities.OfferEntity;
import com.example.demo.entities.Parameters;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OfferController {
    private final OfferRepository offerRepo;
    private final OfferCalculator calc = new OfferCalculator();

    /**
     * Constructor
     * @param offerRepo Repository of offers
     */
    OfferController(OfferRepository offerRepo) {
        this.offerRepo = offerRepo;
    }

    /**
     * GET implementation of offers
     * @return All offers in repository
     */
    @GetMapping("/offers")
    List<OfferEntity> all() {
        return offerRepo.findAll();
    }

    /**
     * GET implementation of individual offers
     * @param id ID of the offer
     * @return Individual offer
     */
    @GetMapping("/offers/{id}")
    OfferEntity one(@PathVariable Long id) {

        return offerRepo.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    /**
     * POST implementation of offers. It adds offers to repository based on the given parameters
     * @param param Parameters of request in order pick up latitude and longitude, drop off latitude and longitude, and number of required passengers
     * @return List of all offers based on the requirements
     */
    @PostMapping("/offers")
    List<OfferEntity> newOffer(@RequestBody Parameters param) {
        offerRepo.deleteAll();

        // Calls the function which calculate the offers
        OfferEntity[] offers = calc.getOffers(param.getPicklat(), param.getPicklong(),
                       param.getDroplat(), param.getDroplong(), param.getPassengers());

        // Save the results in repository
        for (OfferEntity offer : offers) {
                offerRepo.save(offer);
        }

        return offerRepo.findAll();
    }
}
