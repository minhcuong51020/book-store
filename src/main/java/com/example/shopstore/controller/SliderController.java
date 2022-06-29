package com.example.shopstore.controller;

import com.example.shopstore.dto.SliderDTO;
import com.example.shopstore.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class SliderController {

    @Autowired
    private SliderService sliderService;

    @GetMapping(value = {"/admin/slider/add"})
    public String addSliderView(ModelMap modelMap) {
        SliderDTO sliderDTO = new SliderDTO();
        modelMap.addAttribute("sliderDTO", sliderDTO);
        return "admin/sliderAdd";
    }

    @PostMapping(value = {"/admin/slider/add"})
    public String addSlider(ModelMap modelMap, @ModelAttribute("sliderDTO") SliderDTO sliderDTO) {
        Optional<SliderDTO> optional = Optional.ofNullable(sliderService.addSlider(sliderDTO));
        String msg = "";
        if(optional.isPresent()) {
            sliderDTO = new SliderDTO();
            msg = "Thêm sự kiện thành công";
            modelMap.addAttribute("messageSuccess", msg);
        } else {
            msg = "Thêm sự kiện thất bại";
            modelMap.addAttribute("messageFail", msg);
        }
        modelMap.addAttribute("sliderDTO", sliderDTO);
        return "admin/sliderAdd";
    }

    @PostMapping(value = {"/admin/slider/active"})
    public String setIsActiveSlider(ModelMap modelMap, @ModelAttribute("sliderForm") SliderDTO sliderDTO) {
        sliderService.setActiveSlider(sliderDTO.getIsActive(), sliderDTO.getId());
        return "redirect:/admin/slider/list";
    }

    @GetMapping(value = {"/admin/slider/list"})
    public String listSliderView(ModelMap modelMap) {
        List<SliderDTO> sliderDTOS = sliderService.getAllSlider();
        SliderDTO sliderForm = new SliderDTO();
        modelMap.addAttribute("sliderDTOS", sliderDTOS);
        modelMap.addAttribute("sliderForm", sliderForm);
        return "admin/sliderList";
    }

}
