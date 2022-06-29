package com.example.shopstore.validator;

import com.example.shopstore.dto.AuthorDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuthorValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorDTO authorDTO = (AuthorDTO) target;
        if(authorDTO.getName() == null || authorDTO.getName().trim().length() == 0) {
            errors.rejectValue("name", "author.name.required");
        }
    }
}
