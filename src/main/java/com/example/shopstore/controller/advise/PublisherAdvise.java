package com.example.shopstore.controller.advise;

import com.example.shopstore.dto.PublisherDTO;
import com.example.shopstore.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class PublisherAdvise {
    @Autowired
    private PublisherService publisherService;

    @ModelAttribute
    public void publishersIsActive(ModelMap modelMap) {
        List<PublisherDTO> publishers = publisherService.getAllPublisherByIsActive(1);
        modelMap.addAttribute("publishers", publishers);
    }

}
