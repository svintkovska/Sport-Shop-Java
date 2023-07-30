package com.example.springbootshop.controller;

import com.example.springbootshop.entities.Cart;
import com.example.springbootshop.entities.CartItem;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.ok(createdCart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeCart(@PathVariable Long cartId) {
        cartService.removeCart(cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        Cart updatedCart = cartService.addToCart(cartId, cartItem);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        Cart updatedCart = cartService.removeFromCart(cartId, cartItemId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Cart> updateCartItemQuantity(@PathVariable Long cartId,
                                                       @PathVariable Long cartItemId,
                                                       @RequestParam int quantity) {
        Cart updatedCart = cartService.updateCartItemQuantity(cartId, cartItemId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }


}