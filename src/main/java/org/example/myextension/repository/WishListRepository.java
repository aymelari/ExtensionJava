package org.example.myextension.repository;

import lombok.RequiredArgsConstructor;

import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository  extends JpaRepository<WishListEntity,Long> {
   WishListEntity findByUser(UserEntity user);


}
