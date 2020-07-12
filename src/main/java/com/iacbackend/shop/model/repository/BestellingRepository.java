package com.iacbackend.shop.model.repository;

import com.iacbackend.shop.model.Bestelling;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BestellingRepository extends CrudRepository<Bestelling, Integer> {
    @Modifying
        @Transactional
        @Query(value = "UPDATE bestelling SET ordered = true WHERE id = :bestelling_id", nativeQuery = true)
        public void order(@Param("bestelling_id") int bestelling_id);

    @Modifying
        @Transactional
        @Query(value = "UPDATE bestelling SET ordered = false WHERE id = :bestelling_id", nativeQuery = true)
        public void cancel(@Param("bestelling_id") int bestelling_id);

}