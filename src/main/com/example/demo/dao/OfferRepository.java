package com.example.demo.dao;

import com.example.demo.entities.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
}

