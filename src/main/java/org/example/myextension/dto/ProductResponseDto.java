package org.example.myextension.dto;

import lombok.*;

import org.example.myextension.enums.SizeType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String productURL;
    private Double price;
    private String productName;
    private Long userId;
    private String storeName;



}
