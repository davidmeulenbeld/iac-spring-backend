package com.iacbackend.shop.model.repository;

import com.iacbackend.shop.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    @Query(value = "INSERT INTO product_discount VALUES (:discount_id, :product_id)", nativeQuery = true)
    public void updateRelation(@Param("discount_id") int discount_id, @Param("product_id") int product_id);
}