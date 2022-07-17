package com.example.shopstore.controller;

import com.example.shopstore.common.StatusCommon;
import com.example.shopstore.dto.CategoryDTO;
import com.example.shopstore.service.CategoryService;
import com.example.shopstore.validator.CategoryValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private StatusCommon statusCommon;

    @GetMapping(value = "/admin/category/add")
    public String addCategoryView(ModelMap modelMap) {
        CategoryDTO categoryDTO = new CategoryDTO();
        modelMap.addAttribute("categoryDTO", categoryDTO);
        return "admin/categoryAdd";
    }

    @PostMapping(value = "/admin/category/add")
    public String addCategory(ModelMap modelMap,
                              @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                              BindingResult bindingResult) {
        categoryValidator.validate(categoryDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/categoryAdd";
        }
        try {
            categoryService.addCategory(categoryDTO);
        } catch (DataIntegrityViolationException e) {
            modelMap.addAttribute("messageError", "Danh mục đã tồn tại");
            return "admin/categoryAdd";
        }
        return "redirect:/admin/category/list";
    }

    @GetMapping(value = "/admin/category/update/{id}")
    public String updateCategoryView(ModelMap modelMap, @PathVariable("id") Optional<String> optional) {
        if(!optional.isPresent()) {
            return "redirect:/admin/category/list";
        }
        String str = optional.get();
        int id = Integer.parseInt(str);
        Optional<CategoryDTO> optionalCategoryDTO = Optional.ofNullable(categoryService.getCategoryById(id));
        if (!optionalCategoryDTO.isPresent()) {
            return "redirect:/admin/category/list";
        }
        CategoryDTO categoryDTO = optionalCategoryDTO.get();
        modelMap.addAttribute("status", statusCommon.getStatus());
        modelMap.addAttribute("categoryDTO", categoryDTO);
        return "/admin/categoryUpdate";
    }

    @PostMapping(value = "/admin/category/update")
    public String updateCategory(ModelMap modelMap,
                                 @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                                 BindingResult bindingResult) {
        categoryValidator.validate(categoryDTO, bindingResult);
        modelMap.addAttribute("status", statusCommon.getStatus());
        if(bindingResult.hasErrors()) {
            return "admin/categoryUpdate";
        }
        try {
            categoryService.updateCategory(categoryDTO);
        } catch (DataIntegrityViolationException e) {
            modelMap.addAttribute("messageError", "Danh mục đã tồn tại");
            return "admin/categoryUpdate";
        }
        modelMap.addAttribute("categoryDTO", categoryDTO);
        modelMap.addAttribute("messageSuccess", "Cập nhật danh mục thành công");
        return "/admin/categoryUpdate";
    }

    @GetMapping(value = "/admin/category/list")
    public String listCategoryView(ModelMap modelMap) {
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategory();
        modelMap.addAttribute("categoryDTOS", categoryDTOS);
        return "admin/categoryList";
    }

}
