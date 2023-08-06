package com.example.springbootshop.services;

import com.example.springbootshop.dto.*;
import com.example.springbootshop.entities.*;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.OrderItemRepository;
import com.example.springbootshop.repositories.OrderRepository;
import com.example.springbootshop.repositories.OrderStatusRepository;
import com.example.springbootshop.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, CartService cartService, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;

        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    public OrderDTO makeOrder(User user, Cart cart) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(user);
        orderEntity.setDateCreated(LocalDateTime.now());
        orderEntity.setOrderStatus(orderStatusRepository.findByName(EOrderStatus.CREATED).orElseThrow(() -> new EntityNotFoundException("Order status not found")));

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        List<CartItem> cartItems = cartService.getCartItemsByCart(cart);
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderEntity(savedOrderEntity);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            orderItemRepository.save(orderItem);
        }
        List<OrderItemDTO> orderItemsWithQuantities = getOrderItemsWithQuantities(savedOrderEntity.getIdOrder());
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getUsername());
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO(savedOrderEntity.getOrderStatus().getIdOrderStatus(), savedOrderEntity.getOrderStatus().getName());
        OrderDTO orderDTO = new OrderDTO(savedOrderEntity.getIdOrder(), userDTO, orderItemsWithQuantities, orderStatusDTO);

        cartService.clearCart(cart.getIdCart());
        return orderDTO;
    }

    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
    }
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();

        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (OrderEntity order : orders) {
            List<OrderItemDTO> orderItemsWithQuantities = getOrderItemsWithQuantities(order.getIdOrder());
            UserDTO userDTO = new UserDTO(order.getUser().getId(), order.getUser().getName(), order.getUser().getUsername());
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO(order.getOrderStatus().getIdOrderStatus(), order.getOrderStatus().getName());

            OrderDTO orderDTO = new OrderDTO(order.getIdOrder(), userDTO, orderItemsWithQuantities, orderStatusDTO);
            orderDTOs.add(orderDTO);
        }

        return orderDTOs;
    }

    public OrderEntity updateOrderStatus(Long orderId, Long orderStatusId) {
        OrderEntity orderEntity = getOrderById(orderId);
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new EntityNotFoundException("OrderStatus not found with ID: " + orderStatusId));

        orderEntity.setOrderStatus(orderStatus);


        return orderRepository.save(orderEntity);
    }


    public List<OrderWithProductsDTO> getOrderHistory(User user) {
        List<OrderEntity> orders = orderRepository.findByUserId(user.getId());

        List<OrderWithProductsDTO> ordersWithProducts = new ArrayList<>();
        for (OrderEntity order : orders) {
            List<OrderItemDTO> orderItemsWithQuantities = getOrderItemsWithQuantities(order.getIdOrder());
            ordersWithProducts.add(new OrderWithProductsDTO(order, orderItemsWithQuantities));
        }

        return ordersWithProducts;
    }

    public List<OrderItemDTO> getOrderItemsWithQuantities(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        List<OrderItem> orderItemEntities = orderItemRepository.findByOrderEntity(orderEntity);

        List<OrderItemDTO> orderItemsWithQuantities = new ArrayList<>();
        for (OrderItem orderItemEntity : orderItemEntities) {
            Long productId = orderItemEntity.getProduct().getIdProduct();
            int quantity = orderItemEntity.getQuantity();
            Product product = getProductById(productId);

            if (product != null) {
                OrderItemDTO orderItemDTO = new OrderItemDTO(product, quantity);
                orderItemsWithQuantities.add(orderItemDTO);
            }
        }

        return orderItemsWithQuantities;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElse(null);
    }
}
