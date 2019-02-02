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

    OfferController(OfferRepository offerRepo) {
        this.offerRepo = offerRepo;
    }

    @GetMapping("/offers")
    List<OfferEntity> all() {
        return offerRepo.findAll();
    }

    @GetMapping("/offers/{id}")
    OfferEntity one(@PathVariable Long id) {

        return offerRepo.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @PostMapping("/offers")
    List<OfferEntity> newOffer(@RequestBody Parameters param) {

        OfferEntity[] offers = calc.getOffers(param.getPicklat(), param.getPicklong(),
                       param.getDroplat(), param.getDroplong(), param.getPassengers());

        for (OfferEntity offer : offers) {
                offerRepo.save(offer);
        }

        return offerRepo.findAll();
    }
}
