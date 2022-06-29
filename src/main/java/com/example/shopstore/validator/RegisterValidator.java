package com.example.shopstore.validator;

import com.example.shopstore.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String rePassword = userDTO.getRePassword();
        if(username == null || username.trim().length() < 6) {
            errors.rejectValue("username", "field.required");
        } else if(password == null || password.length() < 6) {
            errors.rejectValue("password", "password.length");
        } else if (rePassword == null || !rePassword.equals(userDTO.getRePassword())) {
            errors.rejectValue("rePassword", "password.valid");
        }
    }

}
