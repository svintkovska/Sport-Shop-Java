package com.example.springbootshop.dto;

import com.example.springbootshop.entities.Product;
import lombok.Data;

@Data
public class CartItemDTO {
    private Product product;
    private int quantity;

    public CartItemDTO() {
    }

    public CartItemDTO(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
