package com.example.shopstore.controller.advise;

import com.example.shopstore.dto.AuthorDTO;
import com.example.shopstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class AuthorAdvise {

    @Autowired
    private AuthorService authorService;

    @ModelAttribute
    private void authorsIsActive(ModelMap modelMap) {
        List<AuthorDTO> authors = authorService.getAllAuthorByIsActive(1);
        modelMap.addAttribute("authors", authors);
    }

}
