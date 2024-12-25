package org.example.myextension.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class WishListRequestDto {

    private Long userId; // User who owns the wishlist
    private Set<Long> products;
}
