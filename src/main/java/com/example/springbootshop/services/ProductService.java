package com.example.springbootshop.services;

import com.example.springbootshop.dto.ProductDTO;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }


    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(ProductDTO productDTO) {

        Product product = new Product();
        product.setIdProduct(productDTO.getIdProduct());
        product.setTitle(productDTO.getTitle());
        product.setColor(productDTO.getColor());
        product.setSize(productDTO.getSize());
        product.setBrand(productDTO.getBrand());
        product.setCategory(productDTO.getCategory());
        product.setImages(productDTO.getImages());
        product.setPrice(productDTO.getPrice());
        return productRepository.save(product);
    }


    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Product not found with id " + id));

        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setColor(productDTO.getColor());
        product.setSize(productDTO.getSize());
        product.setBrand(productDTO.getBrand());
        product.setImages(productDTO.getImages());
        product.setCategory(productDTO.getCategory());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
