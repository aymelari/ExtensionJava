package org.example.myextension.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OutfitPackService {
    private final OutfitPackRepository outfitPackRepository;
    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ModelMapper modelMapper;


    public void createOutfit(OutfitPackRequestDto requestDto) {
        UserEntity user = userRepository.findById(requestDto.userId)
                .orElseThrow(() -> new UserNotFoundException("User not found" + userId));

        WishListEntity wishListEntity = wishListRepository.findByUser(user);
        OutfitPackEntity outfitPackEntity = new OutfitPackEntity();
        outfitPackEntity.setUser(user);
        outfitPackEntity.setProductIds(requestDto.getProductIds());
        outfitPackEntity.setOutfitName(requestDto.getOutfitName());
        outfitPackRepository.save(outfitPackEntity);
    }



    public void deleteOutfitPack(Long id) {
        outfitPackRepository.deleteById(id);
    }


   /* public OutfitPackResponseDto editOutfitPack(Long userId,OutfitPackRequestDto requestDto) {
        UserEntity user=userRepository.findById(userId).orElse(null);
        List<OutfitPackResponseDto> allOutfitsByUserId = getAllOutfitsByUserId(userId);
       for(OutfitPackResponseDto outfitPackResponseDto:allOutfitsByUserId) {
           if (outfitPackResponseDto.getId()==requestDto.
       }
        OutfitPackEntity entity = modelMapper.map(requestDto, OutfitPackEntity.class);
        entity.setOutfitName(requestDto.getOutfitName());
        entity.setProducts(requestDto.getProducts());
        outfitPackRepository.save(entity);
        return modelMapper.map(entity, OutfitPackResponseDto.class);
    }*/




    //exception
   /* public OutfitPackResponseDto getOutfitPackByName(String outfitName) {
        OutfitPackEntity byName = outfitPackRepository.findByName(outfitName);
        return modelMapper.map(byName, OutfitPackResponseDto.class);
    }*/




// getAllOutfitsByUserId

    public List<OutfitPackResponseDto> getAllOutfitsByUserId(Long userId) {
        List<OutfitPackEntity> entities = outfitPackRepository.findAll();
        List<OutfitPackEntity> userOutfits = new ArrayList<>();
        for (OutfitPackEntity entity : entities) {
            if (entity.getUser().getId().equals(userId)) {
                userOutfits.add(entity);
            }
        }

        return userOutfits.stream().map(outfitPackEntity
                -> OutfitPackResponseDto.builder()
                        .id(outfitPackEntity.getId())
                .outfitName(outfitPackEntity.getOutfitName())
                .productIds(outfitPackEntity.getProductIds())
                .userId(outfitPackEntity.getUser().getId())
                .build())
                .toList();
    }

    //Optioanl?

    public OutfitPackResponseDto getOutfitById(Long outfitId) {
        OutfitPackEntity outfitPackEntity = outfitPackRepository.findById(outfitId).orElseThrow(()-> new RuntimeException("outfit not found"));
        return OutfitPackResponseDto.builder()
                .id(outfitPackEntity.getId())
                .outfitName(outfitPackEntity.getOutfitName())
                .productIds(outfitPackEntity.getProductIds())
                .userId(outfitPackEntity.getUser().getId())
                .build();
    }


}



