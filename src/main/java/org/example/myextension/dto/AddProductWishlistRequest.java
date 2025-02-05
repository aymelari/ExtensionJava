package org.example.myextension.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddProductWishlistRequest {
    private Long userId;
    private String productURL;
    private Double price;
    private String productName;
    private String storeName;
    private String link;

}
