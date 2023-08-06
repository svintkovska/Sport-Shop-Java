package com.example.springbootshop.dto;

import com.example.springbootshop.entities.OrderStatus;
import com.example.springbootshop.entities.User;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private UserDTO userDto;
    private User user;
    private List<OrderItemDTO> orderItems;
    private OrderStatusDTO orderStatus;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, UserDTO userDto, List<OrderItemDTO> orderItems, OrderStatusDTO orderStatus) {
        this.orderId = orderId;
        this.userDto = userDto;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }
    public OrderDTO(Long orderId, User user, List<OrderItemDTO> orderItems, OrderStatusDTO orderStatus) {
        this.orderId = orderId;
        this.user = user;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }
}
