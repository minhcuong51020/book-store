package com.example.shopstore.controller;

import com.example.shopstore.dto.UserDTO;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ControllerAdvice
public class ControllerAdvise {
    @ModelAttribute
    public void sessionUser(ModelMap modelMap, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        Optional<UserDTO> optional = Optional.ofNullable(userDTO);
        modelMap.addAttribute("currentUser", userDTO);
    }

}
