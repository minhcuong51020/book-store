package com.example.shopstore.controller.advise;

import com.example.shopstore.dto.CategoryDTO;
import com.example.shopstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class CategoryAdvise {
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void categoriesIsActive(ModelMap modelMap) {
        List<CategoryDTO> categories = categoryService.getAllCategoryByIsActiveIs(1);
        modelMap.addAttribute("categories", categories);
    }

}
