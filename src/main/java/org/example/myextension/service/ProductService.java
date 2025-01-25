package org.example.myextension.service;

import lombok.RequiredArgsConstructor;
import org.example.myextension.dto.ProductRequestDto;
import org.example.myextension.dto.ProductResponseDto;
import org.example.myextension.entity.ProductEntity;
import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.example.myextension.repository.ProductRepository;
import org.example.myextension.repository.UserRepository;
import org.example.myextension.repository.WishListRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;

   /* public void saveProduct(ProductRequestDto productRequestDto) {
        UserEntity user = userRepository.findById(productRequestDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        WishListEntity wishlist = wishListRepository.findByUser(user);

        ProductEntity product = ProductEntity.builder().productURL(productRequestDto.getProductURL())
                .price(productRequestDto.getPrice())
                .productName(productRequestDto.getProductName())
                .wishlist(wishlist).build();
        productRepository.save(product);

        wishlist.getProducts().add(product);
        wishListRepository.save(wishlist);
    }*/








}
