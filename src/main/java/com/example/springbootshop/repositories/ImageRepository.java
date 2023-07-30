package com.example.springbootshop.repositories;

import com.example.springbootshop.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("from Images i where i.productId.idProduct = :id")
    Optional<Image> findImageByProductId(@Param("id") Long productId);

    Optional<Image> findImageByUserId(Long userId);
}
