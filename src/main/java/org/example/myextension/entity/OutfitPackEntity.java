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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "outfit_products",
            joinColumns = @JoinColumn(name = "outfit_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"
            )

    )
    private Set<ProductEntity> products;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    Double totalPrice;


}
