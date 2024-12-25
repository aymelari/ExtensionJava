package org.example.myextension.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   /* private String productURL;
    private Double price;
    private String storeName;*/
  /*  @ManyToMany
    Set<WishListEntity> wishlist;

*/




}
