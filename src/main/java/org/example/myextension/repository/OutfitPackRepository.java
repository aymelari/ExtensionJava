package org.example.myextension.repository;




import org.example.myextension.entity.OutfitPackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitPackRepository extends JpaRepository<OutfitPackEntity,Long>{

}