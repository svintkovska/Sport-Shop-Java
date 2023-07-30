package com.example.springbootshop.services;

import com.example.springbootshop.dto.ProductDTO;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.repositories.ProductRepository;
import com.example.springbootshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public List<Product> getAll() {
        return productRepository.findAll();
    }


    public Product getById(Long id) {
        return productRepository.findById(id).orElseGet(Product::new);
    }

    public Product saveProduct(ProductDTO productDTO, Principal principal) {
       User user=  getUserByPrincipal(principal);

        Product product = new Product();
        product.setIdProduct(productDTO.getIdProduct());
        product.setTitle(productDTO.getTitle());
        product.setImages(productDTO.getImages());
        product.setPrice(productDTO.getPrice());
        product.setUser(user);
        return productRepository.save(product);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("Username not found with name " + username));
    }
}
