package org.example.myextension.service;

import lombok.RequiredArgsConstructor;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public void addProductToWishList(AddProductWishlistRequest addProductWishlistRequest) {
        UserEntity user = userRepository.findById(addProductWishlistRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found" + addProductWishlistRequest.getUserId()));
        WishListEntity wishListEntity = wishListRepository.findByUser(user);
        wishListEntity.getProductIds().add(addProductWishlistRequest.getProductId());
        wishListRepository.save(wishListEntity);
    }

    public void removeProductFromWishList(AddProductWishlistRequest addProductWishlistRequest) {


        UserEntity user = userRepository.findById(addProductWishlistRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found" + addProductWishlistRequest.getUserId()));

        WishListEntity wishListEntity = wishListRepository.findByUser(user);
        wishListEntity.getProductIds().remove(addProductWishlistRequest.getProductId());
        wishListRepository.save(wishListEntity);
    }


    public WishListResponseDto getWishList(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found" + userId));

        WishListEntity wishListEntity = wishListRepository.findByUser(user);

        return WishListResponseDto.builder()
                .userId(userId).products(wishListEntity.getProductIds()).build();
    }


    public String generateNonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] nonce = new byte[16]; // Generate a 128-bit nonce
        secureRandom.nextBytes(nonce);
        return Base64.getEncoder().encodeToString(nonce);
    }
}