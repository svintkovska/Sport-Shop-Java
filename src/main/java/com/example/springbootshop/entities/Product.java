package com.example.springbootshop.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_products")
    private Long idProduct;

    @Nullable
    @Size(min = 3, max = 25)
    //@Column(name = "title", nullable = false, length = -1, columnDefinition = "text")
    private String title;
    private Double price;
    private String color;
    private String size;
    private String brand;
    private String description;
    @OneToMany(mappedBy = "productId")
    Set<Image>images;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}
