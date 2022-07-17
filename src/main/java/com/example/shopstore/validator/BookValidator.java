package com.example.shopstore.validator;

import com.example.shopstore.dto.BookDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookDTO bookDTO = (BookDTO) target;
        String title = bookDTO.getTitle();
        String description = bookDTO.getDescription();
        double price = bookDTO.getPrice();
        double discount = bookDTO.getDiscount();
        if(title == null || title.trim().length() == 0) {
            errors.rejectValue("title", "book.title.required");
        }
        if(description == null || description.trim().length() == 0) {
            errors.rejectValue("description", "book.description.required");
        }
        if(price < 0) {
            errors.rejectValue("price", "book.price.required");
        }
        if(discount < 0) {
            errors.rejectValue("discount", "book.discount.required");
        }
    }
}
