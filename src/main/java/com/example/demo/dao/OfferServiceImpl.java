/**
 * Offer service implementation
 * Last updated: 03.02.2019
 *
 * @author Nikolett Bakos
 */
package com.example.demo.dao;

import com.example.demo.entities.OfferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepo;

    @Override
    public long count() {
        return offerRepo.count();
    }

    @Override
    public Iterable<OfferEntity> findAll() {
        Iterable<OfferEntity> offers = offerRepo.findAll();

        return offers;
    }


    @Override
    public <S extends OfferEntity> S save(S entity) {
        return offerRepo.save(entity);
    }
}
