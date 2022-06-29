package com.example.shopstore.validator;

import com.example.shopstore.dto.PublisherDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PublisherValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        PublisherDTO publisherDTO = (PublisherDTO) target;
        String name = publisherDTO.getName();
        int yearFounded = publisherDTO.getYearFounded();
        String description = publisherDTO.getDescription();
        if(name == null || name.trim().length() == 0) {
            errors.rejectValue("name", "publisher.name.required");
        }

        if(yearFounded <= 0) {
            errors.rejectValue("yearFounded", "publisher.year.required");
        }

        if(description == null || description.trim().length() == 0) {
            errors.rejectValue("description", "publisher.description.required");
        }
    }
}
