package com.example.springbootshop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_status")
@Data
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_status")
    private Long idOrderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private EOrderStatus name;

    public OrderStatus() {
    }

    public OrderStatus(EOrderStatus name) {
        this.name = name;
    }

}