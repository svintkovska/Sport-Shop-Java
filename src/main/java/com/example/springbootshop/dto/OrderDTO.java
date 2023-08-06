package com.example.springbootshop.dto;

import com.example.springbootshop.entities.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private UserDTO user;
    private List<OrderItemDTO> orderItems;
    private OrderStatusDTO orderStatus;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, UserDTO user, List<OrderItemDTO> orderItems, OrderStatusDTO orderStatus) {
        this.orderId = orderId;
        this.user = user;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }
}
