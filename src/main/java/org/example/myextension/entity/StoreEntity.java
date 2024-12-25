/*
package org.example.myextension.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.myextension.entity.ProductEntity;


import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class StoreEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

    private String storeName;
    private String storeUrl;
    private String logoUrl; // Optional

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

}
*/
