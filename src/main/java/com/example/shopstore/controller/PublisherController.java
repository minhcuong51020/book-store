package com.example.shopstore.controller;

import com.example.shopstore.common.StatusCommon;
import com.example.shopstore.dto.PublisherDTO;
import com.example.shopstore.service.PublisherService;
import com.example.shopstore.validator.PublisherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @Autowired
    private PublisherValidator publisherValidator;

    @Autowired
    private StatusCommon statusCommon;

    @GetMapping(value = "/admin/publisher/add")
    public String addPublisherView(ModelMap modelMap) {
        PublisherDTO publisherDTO = new PublisherDTO();
        modelMap.addAttribute("publisherDTO", publisherDTO);
        return "admin/publisherAdd";
    }

    @PostMapping(value = "/admin/publisher/add")
    public String addPublisher(ModelMap modelMap,
                               @ModelAttribute("publisherDTO") PublisherDTO publisherDTO,
                               BindingResult bindingResult) {
        publisherValidator.validate(publisherDTO, bindingResult);
        if(!bindingResult.hasErrors()) {
            publisherService.addPublisher(publisherDTO);
            publisherDTO = new PublisherDTO();
            modelMap.addAttribute("publisherDTO", publisherDTO);
            modelMap.addAttribute("messageSuccess", "Thêm nhà xuất bản thành công");
        }
        return "admin/publisherAdd";
    }

    @GetMapping(value = "/admin/publisher/update/{id}")
    public String updatePublisherView(ModelMap modelMap,
                                      @PathVariable("id") Optional<String> optional) {
        modelMap.addAttribute("status", statusCommon.getStatus());
        if(optional.isPresent()) {
            String str = optional.get();
            int id = Integer.parseInt(str);
            Optional<PublisherDTO> optionalPublisherDTO = Optional.ofNullable(publisherService.getPublisherById(id));
            if (!optionalPublisherDTO.isPresent()) {
                return "redirect:/admin/publisher/list";
            }
            PublisherDTO publisherDTO = optionalPublisherDTO.get();
            modelMap.addAttribute("publisherDTO", publisherDTO);
            return "admin/publisherUpdate";
        }
        return "redirect:/admin/publisher/list";
    }

    @PostMapping(value = "/admin/publisher/update")
    public String updatePublisher(ModelMap modelMap,
                                  @ModelAttribute("publisherDTO") PublisherDTO publisherDTO,
                                  BindingResult bindingResult) {
        publisherValidator.validate(publisherDTO, bindingResult);
        modelMap.addAttribute("status", statusCommon.getStatus());
        if(!bindingResult.hasErrors()) {
            publisherDTO = publisherService.updatePublisher(publisherDTO);
            modelMap.addAttribute("publisherDTO", publisherDTO);
            modelMap.addAttribute("messageSuccess", "Cập nhật nhà xuất bản thành công");
        } else {
            modelMap.addAttribute("messageError", "Cập nhật nhà xuất bản thất bại");
        }
        return "admin/publisherUpdate";
    }

    @GetMapping(value = "/admin/publisher/list")
    public String listPublisherView(ModelMap modelMap) {
        List<PublisherDTO>publisherDTOs = publisherService.getAllPublisher();
        modelMap.addAttribute("publisherDTOS", publisherDTOs);
        return "admin/publisherList";
    }
}
