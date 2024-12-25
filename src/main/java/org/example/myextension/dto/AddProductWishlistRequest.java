package org.example.myextension.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddProductWishlistRequest {
    private Long userId;
    private Long productId;
}
