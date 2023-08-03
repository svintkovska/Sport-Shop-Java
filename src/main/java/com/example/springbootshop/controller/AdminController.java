package com.example.springbootshop.controller;

import com.example.springbootshop.dto.OrderDTO;
import com.example.springbootshop.dto.ProductDTO;
import com.example.springbootshop.entities.Category;
import com.example.springbootshop.entities.OrderEntity;
import com.example.springbootshop.entities.OrderStatus;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.facade.ProductFacade;
import com.example.springbootshop.services.CategoryService;
import com.example.springbootshop.services.OrderService;
import com.example.springbootshop.services.OrderStatusService;
import com.example.springbootshop.services.ProductService;
import com.example.springbootshop.validation.ResponseErrorValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin()
public class AdminController {
    private final ProductService productService;
    private final ProductFacade productFacade;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public AdminController(ProductService productService, ProductFacade productFacade, CategoryService categoryService, OrderService orderService, OrderStatusService orderStatusService, ResponseErrorValidation responseErrorValidation) {
        this.productService = productService;
        this.productFacade = productFacade;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.orderStatusService = orderStatusService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/product/create")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);

        if(!ObjectUtils.isEmpty(errors)){
            return errors;
        }
        Product product = productService.saveProduct(productDTO);

        ProductDTO createdProduct = productFacade.productToProductDTO(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }
    @PutMapping("/product/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Product updatedProduct = productService.updateProduct(id, productDTO);
        ProductDTO updatedProductDTO = productFacade.productToProductDTO(updatedProduct);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }
    @PostMapping("/category/create")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }
    @PutMapping("/category/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Category updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/orderStatus/all")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
        List<OrderStatus> orderStatuses = orderStatusService.getAllOrderStatuses();
        return ResponseEntity.ok(orderStatuses);
    }
    @PutMapping("/order/{orderId}/status/{orderStatusId}")
    public ResponseEntity<OrderEntity> updateOrderStatus(@PathVariable Long orderId, @PathVariable Long orderStatusId) {
        OrderEntity updatedOrderEntity = orderService.updateOrderStatus(orderId, orderStatusId);
        return ResponseEntity.ok(updatedOrderEntity);
    }

    @GetMapping("/order/getAll")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }
}
