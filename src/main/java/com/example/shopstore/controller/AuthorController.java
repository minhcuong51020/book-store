package com.example.shopstore.controller;

import com.example.shopstore.common.StatusCommon;
import com.example.shopstore.dto.AuthorDTO;
import com.example.shopstore.service.AuthorService;
import com.example.shopstore.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorValidator authorValidator;

    @Autowired
    private StatusCommon statusCommon;

    @GetMapping(value = "/admin/author/add")
    public String addAuthorView(ModelMap modelMap) {
        AuthorDTO authorDTO = new AuthorDTO();
        modelMap.addAttribute("authorDTO", authorDTO);
        return "/admin/authorAdd";
    }

    @PostMapping(value = "/admin/author/add")
    public String addAuthor(ModelMap modelMap,
                            @ModelAttribute("authorDTO") AuthorDTO authorDTO,
                            BindingResult bindingResult) {
        authorValidator.validate(authorDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            return "/admin/authorAdd";
        }
        authorService.addAuthor(authorDTO);
        authorDTO = new AuthorDTO();
        modelMap.addAttribute("authorDTO", authorDTO);
        modelMap.addAttribute("messageSuccess", "Thêm tác giả thành công");
        return "/admin/authorAdd";
    }

    @GetMapping(value = "/admin/author/update/{id}")
    public String updateAuthorView(ModelMap modelMap, @PathVariable("id") Optional<String> optional) {
        if(!optional.isPresent()) {
            return "redirect:/admin/author/list";
        }
        String str = optional.get();
        int id = Integer.parseInt(str);
        if(!authorService.existsAuthorById(id)) {
            return "redirect:/admin/author/list";
        }
        AuthorDTO authorDTO = authorService.getAuthorById(id);
        modelMap.addAttribute("status", statusCommon.getStatus());
        modelMap.addAttribute("authorDTO", authorDTO);
        return "/admin/authorUpdate";
    }

    @PostMapping(value = "/admin/author/update")
    public String updateAuthor(ModelMap modelMap,
                               @ModelAttribute("authorDTO") AuthorDTO authorDTO,
                               BindingResult bindingResult) {
        authorValidator.validate(authorDTO, bindingResult);
        modelMap.addAttribute("status", statusCommon.getStatus());
        if(bindingResult.hasErrors()) {
            return "/admin/authorUpdate";
        }
        authorDTO = authorService.updateAuthor(authorDTO);
        modelMap.addAttribute("authorDTO", authorDTO);
        modelMap.addAttribute("messageSuccess", "Cập nhật tác giả thành công");
        return "/admin/authorUpdate";
    }

    @GetMapping(value = "/admin/author/list")
    public String listAuthorView(ModelMap modelMap) {
        List<AuthorDTO> authorDTOS = authorService.getAllAuthor();
        modelMap.addAttribute("authorDTOS", authorDTOS);
        return "/admin/authorList";
    }

}
