package com.example.springbootshop.repositories;

import com.example.springbootshop.entities.EOrderStatus;
import com.example.springbootshop.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByName(EOrderStatus eOrderStatus);

}