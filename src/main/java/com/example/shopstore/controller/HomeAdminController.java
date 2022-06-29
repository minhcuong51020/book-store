package com.example.shopstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAdminController {

    @GetMapping("/admin")
    public String homeAdminView() {
        return "admin/home";
    }

    @GetMapping(value = "/403")
    public String pageError() {
        return "403";
    }

}
