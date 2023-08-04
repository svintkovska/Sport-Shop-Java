package com.example.springbootshop.services;

import com.example.springbootshop.entities.Image;
import com.example.springbootshop.entities.Product;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.repositories.ImageRepository;
import com.example.springbootshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;


@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }


    public Image uploadImageToProduct(MultipartFile file, Product product) throws IOException {

        Set<Image> images = product.getImages();
        if (!images.isEmpty()) {
            imageRepository.delete(images.iterator().next());
        }
        Image image = new Image();
        image.setProductId(product);
        image.setImageBytes(file.getBytes());
        image.setName(file.getName());
        return imageRepository.save(image);
    }


    public Image getImageToProduct(Long productId) throws FileNotFoundException {
       Image imageByProductId = imageRepository.findImageByProductId(productId)
                .orElse(null);
        return imageByProductId;
    }


    public Image uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        User user = getUserByPrincipal(principal);

        Image userImage = imageRepository.findImageByUserId(user.getId())
                .orElseThrow(null);

        if(!ObjectUtils.isEmpty(userImage)){
            imageRepository.delete(userImage);
        }
        Image image = new Image();
        image.setUserId(user.getId());
        image.setImageBytes(file.getBytes());
        image.setName(file.getOriginalFilename());

        return image;
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("Username not found with name " + username));
    }

    public Image getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        Image userImage = imageRepository.findImageByUserId(user.getId())
                .orElseThrow(null);

        return userImage;

    }
}
