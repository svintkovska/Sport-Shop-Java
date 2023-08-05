package com.example.springbootshop.services;

import com.example.springbootshop.dto.CartItemDTO;
import com.example.springbootshop.entities.*;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.CartItemRepository;
import com.example.springbootshop.repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Cart existingCart = cartRepository.findByUser(user).orElse(null);
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
        Long productId = cartItem.getProduct().getIdProduct();
        List<CartItem> existingCartItems = cartItemRepository.findByCart(cart);

        CartItem existingCartItem = existingCartItems.stream()
                .filter(item -> item.getProduct().getIdProduct().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(existingCartItem);
        }


        Product product = productService.getById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

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
            if(cartItem.getCart().getIdCart() == cartId){
                cartItemsToDelete.add(cartItem);
            }
        }

        cartItemRepository.deleteAll(cartItemsToDelete);

    }
    public List<CartItem> getCartItemsByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }
    public double getTotalPriceOfCartItems(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            double productPrice = cartItem.getProduct().getPrice().doubleValue();
            int quantity = cartItem.getQuantity();
            double itemTotal = productPrice * quantity;
            totalPrice += itemTotal;
        }

        return totalPrice;
    }
    public List<CartItemDTO> getCartItems(Cart cart){
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
           cartItemDTOs.add(new CartItemDTO(cartItem.getIdCartItem(), cartItem.getProduct(), cartItem.getQuantity()));
        }
        return cartItemDTOs;

    }
}
