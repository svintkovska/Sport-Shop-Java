package com.example.springbootshop.repositories;

import com.example.springbootshop.entities.Cart;
import com.example.springbootshop.entities.CartItem;
import com.example.springbootshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCart(Cart cart);

}
