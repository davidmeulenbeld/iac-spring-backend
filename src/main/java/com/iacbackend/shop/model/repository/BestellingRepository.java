package com.iacbackend.shop.model.repository;

import com.iacbackend.shop.model.Bestelling;
import org.springframework.data.repository.CrudRepository;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BestellingRepository extends CrudRepository<Bestelling, Integer> {

}