package com.example.springbootshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "roles")
@Data

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }
}
