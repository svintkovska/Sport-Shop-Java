package com.example.springbootshop.dto;


import com.example.springbootshop.entities.OrderEntity;
import com.example.springbootshop.entities.Product;
import lombok.Data;

import java.util.List;

@Data
public class OrderWithProductsDTO {
    private OrderEntity order;
    private List<OrderItemDTO> products;

    public OrderWithProductsDTO(OrderEntity order, List<OrderItemDTO> products) {
        this.order = order;
        this.products = products;
    }

    public OrderWithProductsDTO() {
    }
}