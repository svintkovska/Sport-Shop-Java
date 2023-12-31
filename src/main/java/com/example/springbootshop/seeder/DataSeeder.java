package com.example.springbootshop.seeder;

import com.example.springbootshop.entities.*;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component

public class DataSeeder {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, OrderStatusRepository orderStatusRepository, CategoryRepository categoryRepository, ProductRepository productRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role roleUser = new Role(ERole.ROLE_USER);
            Role roleAdmin = new Role(ERole.ROLE_ADMIN);
            roleRepository.saveAll(Arrays.asList(roleUser, roleAdmin));
        }
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new EntityNotFoundException("Admin role not found"));

            User adminUser = new User();
            adminUser.setName("admin");
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.getRoles().add(adminRole);
            userRepository.save(adminUser);
        }
        if (orderStatusRepository.count() == 0) {
            OrderStatus created = new OrderStatus(EOrderStatus.CREATED);
            OrderStatus processing = new OrderStatus(EOrderStatus.PROCESSING);
            OrderStatus completed = new OrderStatus(EOrderStatus.COMPLETED);
            OrderStatus canceled = new OrderStatus(EOrderStatus.CANCELLED);
            orderStatusRepository.saveAll(Arrays.asList(created, processing, completed, canceled));
        }
        if (categoryRepository.count() == 0) {
            List<Category> categories = Arrays.asList(
                    new Category("Shorts"),
                    new Category("Helmets")
            );
            categoryRepository.saveAll(categories);
        }
        if (productRepository.count() == 0) {
            // Get the categories from the database (assuming you already have categories added by CategorySeeder)
            Category shorts = (Category) categoryRepository.findByName("Shorts")
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            Category helmets = (Category) categoryRepository.findByName("Helmets")
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));

            List<Product> products = Arrays.asList(
                    new Product("Gym Shorts", 500.0, "Black", "M", "Puma", "Men's Athletic Shorts, Cotton ", shorts),
                    new Product("Bike Helmet", 800.0, "Blue", "L", "Giro", "Adult Cycling Bike Helmet, Lightweight Unisex", helmets)
            );
            productRepository.saveAll(products);
        }
    }
}
