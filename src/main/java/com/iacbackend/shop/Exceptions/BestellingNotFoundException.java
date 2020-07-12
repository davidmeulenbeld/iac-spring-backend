package com.iacbackend.shop.Exceptions;

public class BestellingNotFoundException extends RuntimeException {
    public BestellingNotFoundException(int id) {
        super("Could not find order with id: " + id);
    }
}
