package org.example.myextension.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateOutfitPackDto {
    Long userId;
    Long outfitPackId;
    String outfitName;
    Set<Long> productIds;
    Double totalPrice;


}
