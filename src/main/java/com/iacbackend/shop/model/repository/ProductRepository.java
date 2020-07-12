package com.iacbackend.shop.model.repository;

import com.iacbackend.shop.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Modifying
        @Transactional
        @Query(value = "UPDATE product SET discount_id = :discount_id WHERE id = :product_id", nativeQuery = true)
        public void updateRelation(@Param("discount_id") int discount_id, @Param("product_id") int product_id);

    @Modifying
        @Transactional
        @Query(value = "INSERT INTO product_category VALUES (:product_id, :category_id)", nativeQuery = true)
        public void addCategory(@Param("product_id") int product_id, @Param("category_id") int category_id);

    @Modifying
        @Transactional
        @Query(value = "INSERT INTO product_bestelling VALUES (:product_id, :bestelling_id)", nativeQuery = true)
        public void addToBestelling(@Param("product_id") int product_id, @Param("bestelling_id") int bestelling_id);

    @Modifying
        @Transactional
        @Query(value = "UPDATE product SET available = :available WHERE id =:product_id", nativeQuery = true)
        public void changeStorage(@Param("product_id") int product_id, @Param("available") int available);
}