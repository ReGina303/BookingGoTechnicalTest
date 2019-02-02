package com.example.demo.controllers;

class OfferNotFoundException  extends RuntimeException {

    OfferNotFoundException(Long id) {
        super("Could not find offer " + id);
    }
}