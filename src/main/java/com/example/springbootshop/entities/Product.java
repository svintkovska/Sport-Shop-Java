package com.example.springbootshop.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


    public Product(@Nullable String title, Double price, String color, String size, String brand, String description, Category category) {
        this.title = title;
        this.price = price;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.description = description;
        this.category = category;
    }


}
