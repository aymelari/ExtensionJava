package org.example.myextension.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.example.myextension.dto.OutfitPackRequestDto;
import org.example.myextension.dto.OutfitPackResponseDto;
import org.example.myextension.dto.DeleteOutfitPackDto;
import org.example.myextension.dto.UpdateOutfitPackDto;
import org.example.myextension.entity.OutfitPackEntity;
import org.example.myextension.entity.ProductEntity;
import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.example.myextension.exceptions.UserNotFoundException;
import org.example.myextension.repository.OutfitPackRepository;

import org.example.myextension.repository.ProductRepository;
import org.example.myextension.repository.UserRepository;
import org.example.myextension.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class OutfitPackService {
    private final OutfitPackRepository outfitPackRepository;
    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;


    public void createOutfit(OutfitPackRequestDto requestDto) {
        log.info("Request User ID: {}", requestDto.getUserId());

        UserEntity user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + requestDto.getUserId()));

        log.info("Found User: {}", user);

        WishListEntity wishListEntity = wishListRepository.findByUser(user);
        if (wishListEntity == null) {
            throw new RuntimeException("Wishlist not found for user ID: " + requestDto.getUserId());
        }

        log.info("Wishlist: {}", wishListEntity);

        Set<ProductEntity> products = requestDto.getProductIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found with the ID: " + productId)))
                .collect(Collectors.toSet());

        log.info("Selected Products: {}", products);

        // Create OutfitPackEntity
        OutfitPackEntity outfitPackEntity = new OutfitPackEntity();
        outfitPackEntity.setUser(user);
        outfitPackEntity.setOutfitName(requestDto.getOutfitName());
        outfitPackEntity.setTotalPrice(requestDto.getTotalPrice());

        // Save OutfitPackEntity first to generate the ID
        OutfitPackEntity savedOutfitPackEntity = outfitPackRepository.save(outfitPackEntity);
        log.info("Outfit Pack saved with ID: {}", savedOutfitPackEntity.getId());

        // Establish bidirectional relationship and update products
        savedOutfitPackEntity.setProducts(products);
        products.forEach(product -> product.getOutfits().add(savedOutfitPackEntity));

        // Save the updated OutfitPackEntity with the products linked
        outfitPackRepository.save(savedOutfitPackEntity);

        log.info("Outfit Pack updated with products successfully");
    }




    public void deleteOutfitPack(DeleteOutfitPackDto deleteOutfitPackDto) {
        try {
            // Extract IDs from DTO
            Long userId = deleteOutfitPackDto.getUserId();
            Long outfitPackId = deleteOutfitPackDto.getOutfitPackId();

            // Validate user existence
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            // Validate outfit pack existence and ownership
            OutfitPackEntity outfitPack = outfitPackRepository.findById(outfitPackId)
                    .orElseThrow(() -> new RuntimeException("Outfit pack not found with ID: " + outfitPackId));

            if (!outfitPack.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Outfit pack does not belong to the specified user.");
            }

            // Delete the outfit pack
            outfitPackRepository.delete(outfitPack);
        } catch (RuntimeException e) {
            // Log the error for debugging purposes (optional)
            e.printStackTrace();
            throw new RuntimeException("Failed to delete outfit pack: " + e.getMessage());
        }
    }



    public List<OutfitPackResponseDto> getAllOutfitsByUserId(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
       Set <OutfitPackEntity> byUser = outfitPackRepository.findByUser(userEntity);
       if (byUser.isEmpty()) {
           throw new RuntimeException("outfit not found with userID: " + userId);
       }
        List<OutfitPackResponseDto> responseDtos = new ArrayList<>();
       for (OutfitPackEntity outfitPackEntity : byUser) {
           Set<Long> productIdsOfOutfitPackEntity = outfitPackEntity.getProducts().stream().map(product -> product.getId()).collect(Collectors.toSet());
           OutfitPackResponseDto build = OutfitPackResponseDto.builder().
                   userId(userId)
                   .id(outfitPackEntity.getId())
                   .outfitName(outfitPackEntity.getOutfitName())
                   .productIds(productIdsOfOutfitPackEntity)
                   .totalPrice(outfitPackEntity.getTotalPrice())
                   .build();

           responseDtos.add(build);
       }
       return responseDtos;

       

    }
    //Optioanl?

    public OutfitPackResponseDto getOutfitById(Long outfitId) {
        OutfitPackEntity outfitPackEntity = outfitPackRepository.findById(outfitId).orElseThrow(()-> new RuntimeException("outfit not found"));
        Set<Long> collect = outfitPackEntity.getProducts().stream().map(productEntity -> productEntity.getId()).collect(Collectors.toSet());
        return OutfitPackResponseDto.builder()
                .id(outfitPackEntity.getId())
                .outfitName(outfitPackEntity.getOutfitName())
                .productIds(collect)
                .userId(outfitPackEntity.getUser().getId())
                .totalPrice(outfitPackEntity.getTotalPrice())
                .build();
    }

    public void updateOutfitPack(UpdateOutfitPackDto updateOutfitPackDto) {
        try {
            // Extract values from DTO
            Long userId = updateOutfitPackDto.getUserId();
            Long outfitPackId = updateOutfitPackDto.getOutfitPackId();
            String newOutfitName = updateOutfitPackDto.getOutfitName();
            Double newTotalPrice=updateOutfitPackDto.getTotalPrice();
            Set<Long> newProductIds = updateOutfitPackDto.getProductIds();

            Set<ProductEntity> products = updateOutfitPackDto.getProductIds().stream()
                    .map(productId -> productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product not found with the id")))
                    .collect(Collectors.toSet());



            // Validate user existence
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            // Validate outfit pack existence and ownership
            OutfitPackEntity outfitPack = outfitPackRepository.findById(outfitPackId)
                    .orElseThrow(() -> new RuntimeException("Outfit pack not found with ID: " + outfitPackId));

            if (!outfitPack.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Outfit pack does not belong to the specified user.");
            }

            // Update outfit name if provided
            if (newOutfitName != null && !newOutfitName.isEmpty()) {
                outfitPack.setOutfitName(newOutfitName);
            }

            // Update product IDs if provided
            if (newProductIds != null && !newProductIds.isEmpty()) {
                outfitPack.setProducts(products);
            }


            if (newTotalPrice != null ) {
                outfitPack.setTotalPrice(newTotalPrice);
            }
            // Save the updated outfit pack
            outfitPackRepository.save(outfitPack);

        } catch (RuntimeException e) {
            e.printStackTrace(); // Log the error (optional)
            throw new RuntimeException("Failed to update outfit pack: " + e.getMessage());
        }
    }


}



