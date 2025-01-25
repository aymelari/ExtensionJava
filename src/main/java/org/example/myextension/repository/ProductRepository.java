package org.example.myextension.repository;


import org.example.myextension.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface

ProductRepository extends JpaRepository<ProductEntity,Long > {
    @Query("SELECT p FROM ProductEntity p WHERE p.wishlist.id = :wishlistId")
    List<ProductEntity> findProductsByWishlistId(@Param("wishlistId") Long wishlistId);
}
