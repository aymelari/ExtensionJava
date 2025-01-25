package org.example.myextension.service;

import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.example.myextension.dto.AddProductWishlistRequest;
import org.example.myextension.dto.ProductRequestDto;
import org.example.myextension.dto.ProductResponseDto;
import org.example.myextension.dto.WishListResponseDto;
import org.example.myextension.entity.ProductEntity;
import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.example.myextension.exceptions.UserNotFoundException;
import org.example.myextension.repository.ProductRepository;
import org.example.myextension.repository.UserRepository;
import org.example.myextension.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public void addProductToWishList(AddProductWishlistRequest addProductWishlistRequest){
        UserEntity user = userRepository.findById(addProductWishlistRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found" + addProductWishlistRequest.getUserId()));
        WishListEntity wishListEntity = wishListRepository.findByUser(user);

        ProductEntity build = ProductEntity.builder().productURL(addProductWishlistRequest.getProductURL())
                .wishlist(wishListEntity)
                .productName(addProductWishlistRequest.getProductName())
                .price(addProductWishlistRequest.getPrice())
                .storeName(addProductWishlistRequest.getStoreName())
                .build();

        productRepository.save(build);
        wishListEntity.getProducts().add(build);
        wishListRepository.save(wishListEntity);


    }
    public void updateProduct(ProductRequestDto productRequestDto, Long productId) {
        UserEntity user = userRepository.findById(productRequestDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        WishListEntity wishlist = wishListRepository.findByUser(user);
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductEntity productToUpdate = wishlist.getProducts()
                .stream()
                .filter(product1 -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in wishlist"));

        productToUpdate.setProductURL(productRequestDto.getProductURL());
        productToUpdate.setPrice(productRequestDto.getPrice());
        productToUpdate.setProductName(productRequestDto.getProductName());
        productToUpdate.setStoreName(productRequestDto.getStoreName());
        productRepository.save(productToUpdate);



    }




    @Transactional
    public WishListResponseDto getWishList(Long userId) {

        log.debug("Fetching user with ID: {}", userId);


        // Using a join fetch to load user and wishlist in one go
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found" + userId));

        log.debug("Fetching wishlist for user ID: {}", userId);
        // Eagerly load wishlist
        WishListEntity wishListEntity = wishListRepository.findByUser(user);

        return WishListResponseDto.builder()
                .userId(userId)
                .products(wishListEntity.getProducts()) // Assuming products is not null
                .build();
    }



    @Transactional
    public void deleteProductFromWishlist(Long userId, Long productId) {
        // Fetch the user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get the user's wishlist
        WishListEntity wishlist = user.getWishlist();
        if (wishlist == null) {
            throw new RuntimeException("Wishlist not found for user");
        }

        // Find the product in the wishlist
        ProductEntity productToRemove = wishlist.getProducts().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in wishlist"));

        // Remove the product from the wishlist
        wishlist.getProducts().remove(productToRemove);

        // Nullify the product's wishlist relationship

        productToRemove.setWishlist(null);
        // Save the updated wishlist to ensure relationship updates
        wishListRepository.save(wishlist);

        // Directly delete the product from the repository
        productRepository.delete(productToRemove);
    }




    public ProductResponseDto getProductDetails(Long productID) {
       /* UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<ProductEntity> products = user.getWishlist().getProducts();
        ProductEntity trueProduct=new ProductEntity();
        for (ProductEntity product : products) {
            if (product.getId().equals(productID)) {
                trueProduct=product;
            }else {
                throw new RuntimeException("Product not found in wishlist");
            }
        }*/
        ProductEntity trueProduct = productRepository.findById(productID).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductResponseDto.builder()
                .productId(trueProduct.getId())
                .productName(trueProduct.getProductName())
                .productURL(trueProduct.getProductURL())
                .price(trueProduct.getPrice())
                .userId(trueProduct.getWishlist().getUser().getId())
                .storeName(trueProduct.getStoreName())
                .build();

    }




}