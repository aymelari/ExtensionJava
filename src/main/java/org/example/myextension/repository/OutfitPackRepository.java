package org.example.myextension.repository;

import org.example.myextension.entity.OutfitPackEntity;
import org.example.myextension.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface OutfitPackRepository extends JpaRepository<OutfitPackEntity,Long>{
 Set<OutfitPackEntity> findByUser(UserEntity user);
}