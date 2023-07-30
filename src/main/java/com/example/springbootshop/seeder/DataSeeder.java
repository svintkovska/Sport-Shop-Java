package com.example.springbootshop.seeder;

import com.example.springbootshop.entities.ERole;
import com.example.springbootshop.entities.Role;
import com.example.springbootshop.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component

public class DataSeeder {
    private final RoleRepository roleRepository;

    public DataSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role roleUser = new Role(ERole.ROLE_USER);
            Role roleAdmin = new Role(ERole.ROLE_ADMIN);
            roleRepository.saveAll(Arrays.asList(roleUser, roleAdmin));
        }
    }
}
