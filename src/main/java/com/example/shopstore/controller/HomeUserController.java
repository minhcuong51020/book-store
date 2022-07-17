package com.example.shopstore.controller;

import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.dto.CategoryDTO;
import com.example.shopstore.dto.SliderDTO;
import com.example.shopstore.entity.Book;
import com.example.shopstore.service.BookService;
import com.example.shopstore.service.CategoryService;
import com.example.shopstore.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeUserController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SliderService sliderService;

    @GetMapping(value = {"/oke"})
    public String home(ModelMap modelMap) {
        List<BookDTO> booksDiscount = bookService.findTopBookByDiscount(0, 1);
        List<BookDTO> booksRandom = bookService.findTopBookRandom(1);
        List<BookDTO> booksNew = bookService.findTopBookNew(1);
        List<SliderDTO> sliderDTOS = sliderService.getAllSliderByIsActive(1);
        modelMap.addAttribute("booksDiscount", booksDiscount);
        modelMap.addAttribute("booksRandom", booksRandom);
        modelMap.addAttribute("booksNew", booksNew);
        modelMap.addAttribute("sliderDTOS", sliderDTOS);
        return "user/home";
    }

    @GetMapping(value = {"/danh-muc/{category}"})
    public String booksView(ModelMap modelMap,
                            @PathVariable("category")Optional<String> category) {
        return booksViewTwo(modelMap, category, Optional.of(1));
    }

    @GetMapping(value = {"/danh-muc/{category}/page/{page}"})
    public String booksViewTwo(ModelMap modelMap,
                               @PathVariable("category") Optional<String> category,
                               @PathVariable("page") Optional<Integer> page) {
        int pageNo = page.orElse(1);
        int pageSize = 4;
        Optional<CategoryDTO> optionalCategory = Optional.ofNullable(
                categoryService.getCategoryBySlugAndIsActive(category.get(), 1));
        Page<BookDTO> pages = bookService.findAllBookByCategoryAndIsActive(category.get(), 1, pageNo, pageSize);
        modelMap.addAttribute("currentPage", pageNo);
        modelMap.addAttribute("totalPages", pages.getTotalPages());
        modelMap.addAttribute("bookDTOS", pages.getContent());
        modelMap.addAttribute("category", optionalCategory.get());
        return "user/bookByCategory";
    }

    @GetMapping(value = {"/sach/chi-tiet/{idBook}"})
    public String bookDetails(ModelMap modelMap,
                               @PathVariable("idBook") Optional<String> idBook) {
        if(!idBook.isPresent()) {
            return "404";
        }
        String str = idBook.get();
        int id = Integer.parseInt(str);
        BookDTO bookDTO = bookService.getBookByIdAndIsActive(id, 1);
        modelMap.addAttribute("bookDTO", bookDTO);
        return "user/bookInfo";
    }

}
