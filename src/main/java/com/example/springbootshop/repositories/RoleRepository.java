package com.example.springbootshop.repositories;

import com.example.springbootshop.entities.ERole;
import com.example.springbootshop.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}