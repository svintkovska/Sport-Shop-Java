package com.example.springbootshop.controller;

import com.example.springbootshop.entities.Image;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.services.ImageService;
import com.example.springbootshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;


    @Autowired
    public ImageController(ImageService imageService, ProductService productService) {
        this.imageService = imageService;
        this.productService = productService;
    }

    @PostMapping("/{productId}/upload")
    public ResponseEntity.BodyBuilder uploadImageToProduct(@PathVariable("productId") Long id,
                                                           @RequestParam("file") MultipartFile file) throws IOException {
        Product product = productService.getById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));;
        imageService.uploadImageToProduct(file, product);
        return ResponseEntity.ok();
    }

    @PostMapping("/upload")
    public  ResponseEntity<Object> uploadImageToUser(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        imageService.uploadImageToUser(file, principal);

        return ResponseEntity.ok("Image uploaded successfully");
    }

    @GetMapping("/userImage")
    public ResponseEntity<Image> getImageForUser(Principal principal){
        Image imageToUser = imageService.getImageToUser(principal);

        return ResponseEntity.ok(imageToUser);
    }

    @GetMapping("{productId}")
    public ResponseEntity<Image> getImageToProduct(@PathVariable("productId") Long id) throws FileNotFoundException {
        Image imageToProduct =
                imageService.getImageToProduct(id);

        if(imageToProduct == null){
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(imageToProduct, HttpStatus.OK);
    }

}