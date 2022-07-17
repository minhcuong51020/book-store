package com.example.shopstore.service.impl;

import com.example.shopstore.common.SlugCommon;
import com.example.shopstore.dto.CategoryDTO;
import com.example.shopstore.entity.Category;
import com.example.shopstore.repository.CategoryRepository;
import com.example.shopstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        String slug = SlugCommon.convertToSlug(categoryDTO.getSlug());
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setSlug(slug);
        Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository.save(category));
        if(optionalCategory.isPresent()) {
            categoryDTO.setId(category.getId());
            categoryDTO.setSlug(category.getSlug());
            categoryDTO.setIsActive(category.getIsActive());
            return categoryDTO;
        }
        return null;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        String slug = SlugCommon.convertToSlug(categoryDTO.getName());
        Category category = new Category(
                categoryDTO.getId(),
                categoryDTO.getName(),
                categoryDTO.getIsActive(),
                slug
        );
        Optional<Category>optional = Optional.ofNullable(categoryRepository.save(category));
        if(!optional.isPresent()) {
            return null;
        }
        return categoryDTO;
    }

    @Override
    public void deleteCategory(int id) {

    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        Optional<List<Category>> optionalCategories = Optional.ofNullable(categoryRepository.findAll());
        if (!optionalCategories.isPresent()) {
            return categoryDTOS;
        }
        List<Category> categories = optionalCategories.get();
        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getId(), category.getName(), category.getIsActive(), category.getSlug()
            );
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    @Override
    public CategoryDTO getCategoryById(int id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            CategoryDTO categoryDTO = new CategoryDTO(
                category.getId(), category.getName(), category.getIsActive(), category.getSlug()
            );
            return categoryDTO;
        }
        return null;
    }

    @Override
    public List<CategoryDTO> getAllCategoryByIsActiveIs(int isActive) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        Optional<List<Category>> optionalCategories = Optional.ofNullable(categoryRepository.getAllByIsActiveIs(isActive));
        if (!optionalCategories.isPresent()) {
            return categoryDTOS;
        }
        List<Category> categories = optionalCategories.get();
        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getId(), category.getName(), category.getIsActive(), category.getSlug()
            );
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    @Override
    public CategoryDTO getCategoryBySlugAndIsActive(String slug, int isActive) {
        Category category = categoryRepository.getCategoryBySlugAndIsActive(slug, isActive);
        CategoryDTO categoryDTO = new CategoryDTO(
                category.getId(), category.getName(), category.getIsActive(), category.getSlug()
        );
        return categoryDTO;
    }
}
