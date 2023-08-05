package com.example.springbootshop.controller;

import com.example.springbootshop.dto.CartItemDTO;
import com.example.springbootshop.entities.Cart;
import com.example.springbootshop.entities.CartItem;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<CartItemDTO>> getCurrentUserCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);
        List<CartItemDTO> cartItems = cartService.getCartItems(cart);
        return ResponseEntity.ok(cartItems);
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

    @PutMapping("/itemQuantity/{cartItemId}") public ResponseEntity<CartItemDTO> updateCartItemQuantity( @PathVariable Long cartItemId,
                                                            @RequestBody int quantity,
                                                            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);

        CartItemDTO updatedCartItem= cartService.updateCartItemQuantity(cart.getIdCart(), cartItemId, quantity);
        if(updatedCartItem == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/clearCart")
    public ResponseEntity<Void> clearCart( Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);

        cartService.clearCart(cart.getIdCart());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/totalPrice")
    public ResponseEntity<Double> getTotalPriceOfCartItems(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);

        double totalPrice = cartService.getTotalPriceOfCartItems(cart);

        return ResponseEntity.ok(totalPrice);
    }


}