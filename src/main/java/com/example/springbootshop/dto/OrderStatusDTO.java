package com.example.springbootshop.dto;

import com.example.springbootshop.entities.EOrderStatus;
import lombok.Data;

@Data
public class OrderStatusDTO {
    private Long orderStatusId;
    private EOrderStatus name;

    public OrderStatusDTO(Long orderStatusId, EOrderStatus name) {
        this.orderStatusId = orderStatusId;
        this.name = name;
    }

}