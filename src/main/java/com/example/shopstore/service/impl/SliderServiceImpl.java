package com.example.shopstore.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.shopstore.entity.Slider;
import com.example.shopstore.dto.SliderDTO;
import com.example.shopstore.repository.SliderRepository;
import com.example.shopstore.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SliderServiceImpl implements SliderService {

    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public SliderDTO addSlider(SliderDTO sliderDTO) {
        MultipartFile multipartFile = sliderDTO.getFile();
        String thumbnail = "";
        if(!multipartFile.isEmpty()) {
            try {
                Map map = this.cloudinary.uploader().upload(sliderDTO.getFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                thumbnail = (String) map.get("secure_url");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
        Slider slider = new Slider();
        slider.setName(sliderDTO.getName());
        slider.setThumbnail(thumbnail);
        Optional<Slider> optional = Optional.ofNullable(sliderRepository.save(slider));
        if(!optional.isPresent()) {
            return null;
        }
        sliderDTO.setId(slider.getId());
        sliderDTO.setIsActive(slider.getIsActive());
        return sliderDTO;
    }

    @Override
    public List<SliderDTO> getAllSlider() {
        List<Slider> sliders = sliderRepository.findAll();
        List<SliderDTO> sliderDTOS = new ArrayList<>();
        for (Slider slider : sliders) {
            SliderDTO sliderDTO = new SliderDTO();
            sliderDTO.setId(slider.getId());
            sliderDTO.setName(slider.getName());
            sliderDTO.setThumbnail(slider.getThumbnail());
            sliderDTO.setIsActive(slider.getIsActive());
            sliderDTOS.add(sliderDTO);
        }
        return sliderDTOS;
    }

    @Override
    public void setActiveSlider(int isActive, int id) {
        sliderRepository.setActiveSlider(isActive, id);
    }

    @Override
    public List<SliderDTO> getAllSliderByIsActive(int isActive) {
        List<Slider> sliders = sliderRepository.findSliderByIsActive(isActive);
        List<SliderDTO> sliderDTOS = new ArrayList<>();
        for (Slider slider : sliders) {
            SliderDTO sliderDTO = new SliderDTO();
            sliderDTO.setId(slider.getId());
            sliderDTO.setName(slider.getName());
            sliderDTO.setThumbnail(slider.getThumbnail());
            sliderDTO.setIsActive(slider.getIsActive());
            sliderDTOS.add(sliderDTO);
        }
        return sliderDTOS;
    }
}
