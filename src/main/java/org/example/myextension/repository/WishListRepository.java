package org.example.myextension.repository;

import lombok.RequiredArgsConstructor;

import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository  extends JpaRepository<WishListEntity,Long> {
   @Query("SELECT w FROM WishListEntity w JOIN FETCH w.products WHERE w.user = :user")
   WishListEntity findByUser(@Param("user") UserEntity user);


}
