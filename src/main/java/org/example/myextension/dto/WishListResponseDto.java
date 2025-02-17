package org.example.myextension.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.myextension.entity.ProductEntity;


import java.util.Set;
@Getter
@Setter
@Builder
public class WishListResponseDto {

    private Long id;
    private Long userId; // User who owns the wishlist
    private Set<ProductEntity> products;
}
