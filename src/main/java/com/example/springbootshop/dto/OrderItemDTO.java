package com.example.springbootshop.dto;

import com.example.springbootshop.entities.Product;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Product product;
    private int quantity;

    public OrderItemDTO(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItemDTO() {
    }

}
