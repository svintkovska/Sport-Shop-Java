package com.example.springbootshop.facade;

import com.example.springbootshop.dto.ProductDTO;
import com.example.springbootshop.entities.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductFacade {
    public ProductDTO productToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setColor(product.getColor());
        productDTO.setSize(product.getSize());
        productDTO.setBrand(product.getBrand());
        productDTO.setDescription(product.getDescription());
        productDTO.setImages(product.getImages());
        productDTO.setCategory(product.getCategory());
        return productDTO;
    }

}
