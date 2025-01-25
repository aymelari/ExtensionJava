package org.example.myextension.controller;

import lombok.RequiredArgsConstructor;
import org.example.myextension.dto.ProductRequestDto;
import org.example.myextension.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
/*
    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.saveProduct(productRequestDto);
        return ResponseEntity.ok("Product saved successfully");
    }*/
   /* @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDto productRequestDto) {
        productService.updateProduct(productRequestDto, productId);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> deleteProductFromWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        productService.deleteProductFromWishlist(userId, productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
*/
}
