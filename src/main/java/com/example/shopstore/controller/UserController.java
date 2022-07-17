package com.example.shopstore.controller;

import com.example.shopstore.dto.UserDTO;
import com.example.shopstore.service.UserService;
import com.example.shopstore.validator.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private RegisterValidator registerValidator;

    @GetMapping(value = "/login")
    public String loginView(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        if(userDTO != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping(value = "/login?logout")
    public String logoutView() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String registerView(ModelMap modelMap) {
        UserDTO userDTO = new UserDTO();
        modelMap.addAttribute("userDTO", userDTO);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(ModelMap modelMap, @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result) {
        registerValidator.validate(userDTO, result);
        if(result.hasErrors()) {
            return "register";
        } else {
            if (this.userDetailsService.addUser(userDTO)) {
                return "redirect:/login";
            }
        }
        return "register";
    }

    @GetMapping(value = "/user/info")
    public String userInfoView(ModelMap modelMap, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        modelMap.addAttribute("userDTO", userDTO);
        return "/user/userInfo";
    }

    @GetMapping(value = "/user/info/update")
    public String userInfoUpdate(ModelMap modelMap, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        modelMap.addAttribute("userDTO", userDTO);
        return "/user/userInfoUpdate";
    }

    @PostMapping(value = "/user/info/update")
    public String userInfoUpdate(ModelMap modelMap, @ModelAttribute("userDTO") UserDTO userDTO) {
        UserDTO userDTONew = userDetailsService.updateUser(userDTO);
        String msg = "";
        if(userDTONew != null) {
            msg = "Cập nhật thành công";
            modelMap.addAttribute("userDTO", userDTONew);
            modelMap.addAttribute("messageSuccess", msg);
        } else {
            msg = "Cập nhật thất bại";
            modelMap.addAttribute("userDTO", userDTO);
            modelMap.addAttribute("messageError", msg);
        }
        return "/user/userInfoupdate";
    }

}
