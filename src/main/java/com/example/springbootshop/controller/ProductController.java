package com.example.springbootshop.controller;

import com.example.springbootshop.dto.ProductDTO;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.facade.ProductFacade;
import com.example.springbootshop.services.ProductService;
import com.example.springbootshop.validation.ResponseErrorValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product")
@CrossOrigin()
public class ProductController {

    private final ProductService productService;

    private final ProductFacade productFacade;

    private final ResponseErrorValidation responseErrorValidation;


    @Autowired
    public ProductController(ProductService productService, ProductFacade productFacade, ResponseErrorValidation responseErrorValidation) {
        this.productService = productService;
        this.productFacade = productFacade;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> allProducts = productService.getAll();

        List<ProductDTO> productDTOList = allProducts.stream()
                .map(productFacade::productToProductDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product productById = productService.getById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }





}
