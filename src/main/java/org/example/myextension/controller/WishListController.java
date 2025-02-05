package org.example.myextension.controller;

import lombok.RequiredArgsConstructor;

import org.example.myextension.dto.AddProductWishlistRequest;
import org.example.myextension.dto.ProductRequestDto;
import org.example.myextension.dto.ProductResponseDto;
import org.example.myextension.dto.WishListResponseDto;
import org.example.myextension.service.ProductService;
import org.example.myextension.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishList")
public class WishListController {
    private final WishListService wishListService;



    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PutMapping("/add")
    public void addProductToWishList(@RequestBody AddProductWishlistRequest addProductWishlistRequest) {
        wishListService.addProductToWishList(addProductWishlistRequest);

    }


    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDto productRequestDto) {
        wishListService.updateProduct(productRequestDto, productId);
        return ResponseEntity.ok("Product updated successfully");
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<WishListResponseDto> getWishList(@PathVariable @RequestBody Long userId) {
        return ResponseEntity.ok(wishListService.getWishList(userId));
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> deleteProductFromWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        wishListService.deleteProductFromWishlist(userId, productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

   @GetMapping("/{productId}")
   public ResponseEntity<ProductResponseDto> getProductDetails (/*(@PathVariable Long userId,*/ @PathVariable  Long productId){
        return ResponseEntity.ok(wishListService.getProductDetails( productId));
   }




}

