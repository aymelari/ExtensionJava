package org.example.myextension.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
 private String productURL;
    private Double price;
    private String productName;
    private Long userId;
    private String storeName;

}
