package com.example.demo.dao;

import com.example.demo.entities.OfferEntity;

public interface OfferService {
    public long count();

    public <S extends OfferEntity> S save(S entity);
}
