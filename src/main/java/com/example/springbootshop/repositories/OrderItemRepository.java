package com.example.springbootshop.repositories;

import com.example.springbootshop.entities.OrderEntity;
import com.example.springbootshop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderEntity(OrderEntity orderEntity);

}
