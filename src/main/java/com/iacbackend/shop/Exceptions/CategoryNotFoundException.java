package com.iacbackend.shop.Exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(int id) {
        super("Could not find category with id: " + id);
    }
}
