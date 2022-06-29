package com.example.shopstore.validator;


import com.example.shopstore.dto.CategoryDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryDTO categoryDTO = (CategoryDTO) target;
        if(categoryDTO.getName() == null || categoryDTO.getName().length() == 0) {
            errors.rejectValue("name", "category.name.required");
        }
    }
}
