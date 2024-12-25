package org.example.myextension.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

  private  String name;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)

    private UserEntity user; //



    @ElementCollection
    @CollectionTable(name = "wishlist_product_ids",
            joinColumns = @JoinColumn(name = "wishlist_id")) // Foreign key to WishListEntity
    @Column(name = "product_id")
    private Set<Long> productIds;
}
