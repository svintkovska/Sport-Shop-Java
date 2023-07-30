package com.example.springbootshop.services;

import com.example.springbootshop.entities.Category;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseGet(Category::new);
    }
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
    public Category updateCategory(Long id, Category category) {
        Category cat = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found with id " + id));

        cat.setName(category.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
