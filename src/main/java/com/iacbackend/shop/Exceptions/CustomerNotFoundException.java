package com.iacbackend.shop.Exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(int id) {
        super("Could not find customer with id: " + id);
    }
}
