package com.example.springbootshop.controller;

import com.example.springbootshop.dto.OrderWithProductsDTO;
import com.example.springbootshop.entities.Cart;
import com.example.springbootshop.entities.OrderEntity;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.services.CartService;
import com.example.springbootshop.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    public final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping("/makeOrder")
    public ResponseEntity<OrderEntity> makeOrder(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);
        OrderEntity createdOrderEntity = orderService.makeOrder(user, cart);
        return ResponseEntity.ok(createdOrderEntity);
    }
    @GetMapping("/history")
    public ResponseEntity<List<OrderWithProductsDTO>> getOrderHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<OrderWithProductsDTO> orderHistory = orderService.getOrderHistory(user);
        return ResponseEntity.ok(orderHistory);
    }
//    @GetMapping("/{orderId}")
//    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
//        Order order = orderService.getOrderById(orderId);
//        return ResponseEntity.ok(order);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Order>> getAllOrders() {
//        List<Order> orders = orderService.getAllOrders();
//        return ResponseEntity.ok(orders);
//    }
}