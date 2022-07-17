package com.example.shopstore.service;

import com.example.shopstore.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    public CategoryDTO addCategory(CategoryDTO categoryDTO);

    public CategoryDTO updateCategory(CategoryDTO categoryDTO);

    public void deleteCategory(int id);

    public List<CategoryDTO> getAllCategory();

    public CategoryDTO getCategoryById(int id);

    public List<CategoryDTO> getAllCategoryByIsActiveIs(int isActive);

    public CategoryDTO getCategoryBySlugAndIsActive(String slug, int isActive);

}
