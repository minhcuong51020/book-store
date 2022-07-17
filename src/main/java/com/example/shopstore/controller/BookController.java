package com.example.shopstore.controller;

import com.example.shopstore.common.StatusCommon;
import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.service.BookService;
import com.example.shopstore.validator.BookValidator;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private BookService bookService;

    @Autowired
    private StatusCommon statusCommon;

    @GetMapping(value = {"/admin/book/add"})
    public String addBookView(ModelMap modelMap) {
        BookDTO bookDTO = new BookDTO();
        modelMap.addAttribute("bookDTO", bookDTO);
        return "/admin/bookAdd";
    }

    @PostMapping(value = {"/admin/book/add"})
    public String addBook(ModelMap modelMap,
                          @ModelAttribute("bookDTO") BookDTO bookDTO,
                          BindingResult bindingResult) {
        bookValidator.validate(bookDTO, bindingResult);
        String message = "";
        if(bindingResult.hasErrors()) {
            message = "Thêm sách thất bại";
            modelMap.addAttribute("messageFail", message);
        } else {
            Optional<BookDTO> optional = Optional.ofNullable(bookService.addBook(bookDTO));
            if(optional.isPresent()) {
                bookDTO = new BookDTO();
                modelMap.addAttribute("bookDTO", bookDTO);
                message = "Thêm sách thành công";
                modelMap.addAttribute("messageSuccess", message);
            } else {
                message = "Thêm sách thất bại";
                modelMap.addAttribute("messageFail", message);
            }
        }
        return "admin/bookAdd";
    }

    @GetMapping(value = {"/admin/book/update/{id}"})
    public String updateBookView(ModelMap modelMap, @PathVariable("id") Optional<String> optional) {
        if(!optional.isPresent()) {
            return "redirect:/admin/book/list";
        }
        String str = optional.get();
        int id = Integer.parseInt(str);
        if(!bookService.existBookById(id)) {
            return "redirect:/admin/book/list";
        }
        BookDTO bookDTO = bookService.getBookById(id);
        modelMap.addAttribute("bookDTO", bookDTO);
        modelMap.addAttribute("status", statusCommon.getStatus());
        return "admin/bookUpdate";
    }

    @PostMapping(value = {"/admin/book/update"})
    public String updateBook(ModelMap modelMap,
                             @ModelAttribute("bookDTO") BookDTO bookDTO,
                             BindingResult bindingResult) {
        bookValidator.validate(bookDTO, bindingResult);
        modelMap.addAttribute("status", statusCommon.getStatus());
        String message = "Cập nhật sách thành công";
        if(bindingResult.hasErrors()) {
            message = "Cập nhật sách thất bại";
            modelMap.addAttribute("messageError", message);
        } else {
            System.out.println(bookDTO.getAuthorId());
            bookService.updateBook(bookDTO);
            modelMap.addAttribute("messageSuccess", message);
        }
        return "admin/bookUpdate";
    }

    @GetMapping(value = {"/admin/book/list"})
    public String listBookView(ModelMap modelMap) {
        List<BookDTO> bookDTOS = bookService.getAllBook();
        modelMap.addAttribute("bookDTOS", bookDTOS);
        return "admin/bookList";
    }

}
