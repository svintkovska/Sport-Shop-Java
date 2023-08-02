package com.example.springbootshop.services;

import com.example.springbootshop.entities.*;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.CartItemRepository;
import com.example.springbootshop.repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public Cart createOrGetCartForUser(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();

        Cart existingCart = cartRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        if (existingCart != null) {
            return existingCart;
        }

        Cart newCart = new Cart();
        newCart.setUser(user);

        return cartRepository.save(newCart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for the user"));
    }


    public CartItem addToCart(Cart cart, CartItem cartItem) {
        Long id = cartItem.getProduct().getIdProduct();
        Product product = productService.getById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        return cartItemRepository.save(cartItem);
    }


    public Cart removeFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        CartItem cartItemToRemove = cartItemRepository.getById(cartItemId);
        cart.getCartItems().remove(cartItemToRemove);
        cartItemToRemove.setCart(null);
        cartItemRepository.delete(cartItemToRemove);

        return cartRepository.save(cart);
    }

    public CartItem updateCartItemQuantity(Long cartId, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.getById(cartItemId);

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cartItem;
    }
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cartItemRepository.findAll();
        List<CartItem> cartItemsToDelete = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            if(cartItem.getIdCartItem() == cartId){
                cartItemsToDelete.add(cartItem);
            }
        }

        cartItemRepository.deleteAll(cartItemsToDelete);

    }


}
