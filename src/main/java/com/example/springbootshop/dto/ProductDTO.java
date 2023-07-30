package com.example.springbootshop.dto;

import com.example.springbootshop.entities.Category;
import com.example.springbootshop.entities.Image;
import lombok.Data;

import java.util.Set;

@Data
public class ProductDTO {
    private Long idProduct;
    private String title;
    private Double price;
    private Rating rating;
    private Category category;
    Set<Image> images;
}
