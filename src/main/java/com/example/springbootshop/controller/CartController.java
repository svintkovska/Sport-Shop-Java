package com.example.springbootshop.controller;

import com.example.springbootshop.entities.Cart;
import com.example.springbootshop.entities.CartItem;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping("/")
    public ResponseEntity<Cart> getCurrentUserCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/addItem")
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem cartItem, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);
        CartItem newItem =  cartService.addToCart(cart, cartItem);
        return ResponseEntity.ok(newItem);
    }


    @DeleteMapping("/removeItem/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart( @PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);
        Cart updatedCart = cartService.removeFromCart(cart.getIdCart(), cartItemId);

        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/itemQuantity/{cartItemId}") public ResponseEntity<CartItem> updateCartItemQuantity( @PathVariable Long cartItemId,
                                                            @RequestBody int quantity,
                                                            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);

        CartItem updatedCartItem= cartService.updateCartItemQuantity(cart.getIdCart(), cartItemId, quantity);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/clearCart")
    public ResponseEntity<Void> clearCart( Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);

        cartService.clearCart(cart.getIdCart());
        return ResponseEntity.ok().build();
    }


}