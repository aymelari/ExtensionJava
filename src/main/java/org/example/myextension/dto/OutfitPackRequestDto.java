package org.example.myextension.dto;

import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class OutfitPackRequestDto {

    private String outfitName;
    private Set<Long> productIds;
    private Long userId;
}
