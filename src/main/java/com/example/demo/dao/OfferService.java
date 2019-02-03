/**
 * Offer service interface
 * Last updated: 03.02.2019
 *
 * @author Nikolett Bakos
 */
package com.example.demo.dao;

import com.example.demo.entities.OfferEntity;

public interface OfferService {
    public long count();

    public Iterable<OfferEntity> findAll();

    public <S extends OfferEntity> S save(S entity);
}
