/**
 * Offer repository
 * Last updated: 03.02.2019
 *
 * @author Nikolett Bakos
 */
package com.example.demo.dao;

import com.example.demo.entities.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
}

