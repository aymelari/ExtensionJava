package org.example.myextension.controller;

import lombok.RequiredArgsConstructor;

import org.example.myextension.dto.AddProductWishlistRequest;
import org.example.myextension.dto.WishListResponseDto;
import org.example.myextension.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishList")
public class WishListController {
    private final WishListService wishListService;


    @PutMapping("/add")
    public void addProductToWishList(@RequestBody AddProductWishlistRequest addProductWishlistRequest) {
        wishListService.addProductToWishList(addProductWishlistRequest);

    }

    @DeleteMapping("/remove/")
    public void removeProductFromWishList(@RequestBody AddProductWishlistRequest addProductWishlistRequest) {

        wishListService.removeProductFromWishList(addProductWishlistRequest);
    }


    @GetMapping("/get/{userId}")
    public ResponseEntity<WishListResponseDto> getWishList(@PathVariable @RequestBody Long userId) {
        return ResponseEntity.ok(wishListService.getWishList(userId));
    }

    @GetMapping("/popup")
    @ResponseBody
    public String getPopupPage(@RequestParam Long userId) {
        String nonce = wishListService.generateNonce();  // Generate the nonce

        // Create the HTML with the nonce
        String html = "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'/>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'/>" +
                "<title>Create and Fetch User</title>" +
                // Content Security Policy with nonce for scripts and styles
                "<meta http-equiv='Content-Security-Policy' content='script-src \"self\" \"nonce-" + nonce + "\"; style-src \"self\" \"nonce-" + nonce + "\";'/>" +
                // External JavaScript files with nonce attribute
                "<script src='wishlist.js' nonce='" + nonce + "'></script>" +
                "<script src='popup.js' nonce='" + nonce + "'></script>" +
                "</head>" +
                "<body>" +
                "<h2>Create a New User</h2>" +
                "<form id='createUserForm'>" +
                "<label for='username'>Username:</label>" +
                "<input type='text' id='username' name='username' required />" +
                "<label for='email'>Email:</label>" +
                "<input type='email' id='email' name='email' required />" +
                "<button type='submit'>Create User</button>" +
                "</form>" +
                "<h2>Get User by ID</h2>" +
                "<form id='getUserForm'>" +
                "<label for='userId'>User ID:</label>" +
                "<input type='text' id='userId' name='userId' required />" +
                "<button type='submit' id='getUserButton'>Get User</button>" +
                "</form>" +
                "<h2>Fetch and View User's Wishlist</h2>" +
                "<div class='wishlist-form'>" +
                "<label for='userIdForWishlist'>User ID for Wishlist:</label>" +
                "<input type='text' id='userIdForWishlist' name='userIdForWishlist' required />" +
                "<button type='button' class='wishlist-button' id='getWishlistButton'>Get Wishlist</button>" +
                "</div>" +
                "<h2>Add Product to Wishlist</h2>" +
                "<div class='wishlist-form'>" +
                "<label for='productId'>Product ID:</label>" +
                "<input type='text' id='productId' name='productId' required />" +
                "<label for='userIdForWishlistAdd'>User ID:</label>" +
                "<input type='text' id='userIdForWishlistAdd' name='userIdForWishlist' required />" +
                "<button type='submit' id='addProductToWishlistButton' class='wishlist-button'>Add Product to Wishlist</button>" +
                "</div>" +
                "<div class='message' id='message'></div>" +
                "</body>" +
                "</html>";

        return html;
    }




}

