package com.example.springbootshop.services;

import com.example.springbootshop.repositories.OrderStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

}