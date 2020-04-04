package com.iacbackend.shop.model.repository;

import com.iacbackend.shop.model.Addres;
import org.springframework.data.repository.CrudRepository;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AdresRepository extends CrudRepository<Addres, Integer> {

}