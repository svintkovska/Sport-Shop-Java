package com.example.springbootshop.services;

import com.example.springbootshop.entities.Order;
import com.example.springbootshop.entities.OrderStatus;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.OrderRepository;
import com.example.springbootshop.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    public Order createOrder(Order order) {
        order.setDateCreated(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
    public Order updateOrderStatus(Long orderId, Long orderStatusId) {
        Order order = getOrderById(orderId);
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new EntityNotFoundException("OrderStatus not found with ID: " + orderStatusId));

        order.setOrderStatus(orderStatus);


        return orderRepository.save(order);
    }
}
