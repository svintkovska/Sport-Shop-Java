package com.example.springbootshop.services;

import com.example.springbootshop.entities.Cart;
import com.example.springbootshop.entities.CartItem;
import com.example.springbootshop.entities.Category;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void removeCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public Cart addToCart(Long cartId, CartItem cartItem) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartItems().add(cartItem);
        return cartRepository.save(cart);
    }

    public Cart removeFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartItems().removeIf(item -> item.getIdCartItem().equals(cartItemId));
        return cartRepository.save(cart);
    }

    public Cart updateCartItemQuantity(Long cartId, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        for (CartItem item : cart.getCartItems()) {
            if (item.getIdCartItem().equals(cartItemId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        return cartRepository.save(cart);
    }

    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }


}
