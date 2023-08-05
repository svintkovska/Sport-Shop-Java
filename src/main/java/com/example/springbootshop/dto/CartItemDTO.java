package com.example.springbootshop.dto;

import com.example.springbootshop.entities.Product;
import lombok.Data;

@Data
public class CartItemDTO {
    private long id;
    private Product product;
    private int quantity;

    public CartItemDTO() {
    }

    public CartItemDTO(long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }
}
