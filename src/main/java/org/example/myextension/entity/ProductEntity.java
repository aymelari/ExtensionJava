package org.example.myextension.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productURL;
    private Double price;
    private String productName;
    private String storeName;
    @Column(name = "link", length = 2000)
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = true)
    @JsonBackReference
    private WishListEntity wishlist;

    @ManyToMany(mappedBy = "products",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OutfitPackEntity> outfits;





}
