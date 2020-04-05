package com.iacbackend.shop.Exceptions;

public class DiscountNotFoundException extends RuntimeException {
    public DiscountNotFoundException(int id) {
        super("Could not find discount with id: " + id);
    }
}