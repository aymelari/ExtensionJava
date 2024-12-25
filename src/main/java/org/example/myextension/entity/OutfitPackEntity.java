package org.example.myextension.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="outfit_pack")
public class OutfitPackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String outfitName;

    @ElementCollection
    @CollectionTable(name = "wishlist_product_ids",
            joinColumns = @JoinColumn(name = "wishlist_id")) // Foreign key to WishListEntity
    @Column(name = "product_id")
    private Set<Long> productIds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
