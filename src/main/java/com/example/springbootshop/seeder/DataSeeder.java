package com.example.springbootshop.seeder;

import com.example.springbootshop.entities.EOrderStatus;
import com.example.springbootshop.entities.ERole;
import com.example.springbootshop.entities.OrderStatus;
import com.example.springbootshop.entities.Role;
import com.example.springbootshop.repositories.OrderStatusRepository;
import com.example.springbootshop.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component

public class DataSeeder {
    private final RoleRepository roleRepository;
    private final OrderStatusRepository orderStatusRepository;

    public DataSeeder(RoleRepository roleRepository, OrderStatusRepository orderStatusRepository) {
        this.roleRepository = roleRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role roleUser = new Role(ERole.ROLE_USER);
            Role roleAdmin = new Role(ERole.ROLE_ADMIN);
            roleRepository.saveAll(Arrays.asList(roleUser, roleAdmin));
        }
        if (orderStatusRepository.count() == 0) {
            OrderStatus created = new OrderStatus(EOrderStatus.CREATED);
            OrderStatus processing = new OrderStatus(EOrderStatus.PROCESSING);
            OrderStatus completed = new OrderStatus(EOrderStatus.COMPLETED);
            OrderStatus canceled = new OrderStatus(EOrderStatus.CANCELLED);
            orderStatusRepository.saveAll(Arrays.asList(created, processing, completed, canceled));
        }
    }
}
