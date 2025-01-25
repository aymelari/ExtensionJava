package org.example.myextension.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
public class OutfitPackResponseDto {

    Long id;
    private  String outfitName;
    private Set<Long> productIds;
    private Long userId;
    private Double totalPrice;
}
